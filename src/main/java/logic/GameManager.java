package logic;

import entity.base.Character;
import entity.base.GameObject;
import entity.playerunit.Carrot;
import entity.playerunit.Onion;
import entity.unit.Projectile;
import gui.*;
import javafx.animation.AnimationTimer;
import javafx.geometry.Dimension2D;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GameManager {
    private PlayerTurn currentPlayerTurn;
    private TurnState currentTurnState;

    public ArrayList<GameObject> allObjects = new ArrayList<>();
    public boolean isGameOver = false;

    private StackPane root;
    private IntroMenu introMenu;
    private MainMenu mainMenu;
    private HowToMenu howToMenu;
    private GamePane gamePane;
    private PlayerHpBarPane playerHpBarPane;
    private PowerBarPane powerBarPane;
    private Canvas gameCanvas;

    private Carrot carrot;
    private Onion onion;
    private AnimationTimer gameLoop;

    private Projectile currentProjectile;
    private double currentPower;
    private final double MAX_POWER = 100;
    private final double MIN_POWER = 10;
    private final double POWER_CHARGE_RATE = 2;

    private long hitTimestamp;
    private final long HIT_STATE_DURATION_MS = 1000; // 1 second

    private TurnTimer turnTimer;
    private TurnTimerPane turnTimerPane;
    private long lastFrameTime;

    private ImageCursor carrotCursor;
    private ImageCursor onionCursor;


    public GameManager(StackPane root) {
        this.root = root;
        this.introMenu = new IntroMenu();
        this.mainMenu = new MainMenu();
        this.howToMenu = new HowToMenu();
        this.gameCanvas = new Canvas(1280, 720);

        introMenu.setOnAction(this::showMainMenu);
        mainMenu.setOnHowToAction(this::showHowToMenu);
        mainMenu.setOnStartAction(this::startGame);
        howToMenu.setOnStartAction(this::startGame);

        double cursorSize = 64;

        Image carrotImg = new Image(getClass().getResourceAsStream("/projectileImage/throwingCarrot.png"));
        Image onionImg = new Image(getClass().getResourceAsStream("/projectileImage/throwingOnion.png"));

        double hotSpotX = cursorSize / 2;
        double hotSpotY = cursorSize / 2;

        this.carrotCursor = new ImageCursor(carrotImg, hotSpotX, hotSpotY);
        this.onionCursor = new ImageCursor(onionImg, hotSpotX, hotSpotY);

    }

    public void start() {
        root.getChildren().add(introMenu);
    }

    private void showMainMenu() {
        root.getChildren().clear();
        root.getChildren().add(mainMenu);
    }

    private void showHowToMenu() {
        root.getChildren().clear();
        root.getChildren().add(howToMenu);
    }

    private void startGame() {
        allObjects.clear();

        final double GROUND_Y = 660;

        this.carrot = new Carrot(200, 0, 100);
        this.onion = new Onion(980, 0, 100);

        this.carrot.setY(GROUND_Y - this.carrot.getHeight());
        this.onion.setY(GROUND_Y - this.onion.getHeight());
        allObjects.add(carrot);
        allObjects.add(onion);

        this.gamePane = new GamePane(gameCanvas, allObjects);
        this.playerHpBarPane = new PlayerHpBarPane(carrot, onion);
        this.powerBarPane = new PowerBarPane(this);

        /// TurnTimer
        this.turnTimer = new TurnTimer(10.0);

        this.turnTimerPane = new TurnTimerPane();

        root.getChildren().clear();
        root.getChildren().addAll(gamePane, playerHpBarPane, powerBarPane,turnTimerPane);

        ///  Start counting
        lastFrameTime = System.nanoTime();

        setupInputHandlers();

        currentPlayerTurn = PlayerTurn.PLAYER_ONE;
        currentTurnState = TurnState.READY;
        currentPower = MIN_POWER;

        updateCursor();

        isGameOver = false;
        gameLoop = new GameLoop(this, gamePane, playerHpBarPane, allObjects);
        gameLoop.start();
    }

    private void setupInputHandlers() {
        root.getScene().setOnMousePressed(e -> handleMousePressed(e.getButton(), e.getX(), e.getY()));
        root.getScene().setOnMouseReleased(e -> handleMouseReleased(e.getButton()));
    }

    private void handleMousePressed(MouseButton button, double x, double y) {
        if (button == MouseButton.PRIMARY && currentTurnState == TurnState.READY) {
            if (getActivePlayer().getHitBox().contains(x, y)) {
                currentTurnState = TurnState.CHARGING;
                currentPower = MIN_POWER;
                getActivePlayer().setAttacking(true);
            }
        }
    }

    private void handleMouseReleased(MouseButton button) {
        if (button == MouseButton.PRIMARY && currentTurnState == TurnState.CHARGING) {
            getActivePlayer().setAttacking(false);
            launchProjectile();
            currentTurnState = TurnState.PROJECTILE_IN_AIR;
        }
    }

    private void launchProjectile() {
        Character activePlayer = getActivePlayer();
        double startX;
        double startY = activePlayer.getY();
        double angle;

        javafx.scene.image.Image projectileImage = (activePlayer instanceof Onion) ?
                ((Onion) activePlayer).getProjectileImage() :
                ((Carrot) activePlayer).getProjectileImage();

        // Firing position logic is now swapped
        if (currentPlayerTurn == PlayerTurn.PLAYER_ONE) {
            startX = activePlayer.getX() - activePlayer.getWidth()/4;
            angle = -45;
        } else {
            startX = activePlayer.getX() + activePlayer.getWidth()/4;
            angle = -135;
        }

        // Adjust startX to account for the projectile's own width


        currentProjectile = new Projectile(startX, startY, projectileImage, angle, currentPower / 5.0);
        allObjects.add(currentProjectile);
    }

    public void update() {
        if (isGameOver) return;

        /// Timer
        long now = System.nanoTime();
        double deltaTime = (now - lastFrameTime) / 1_000_000_000.0;
        lastFrameTime = now;

        if (currentTurnState == TurnState.READY || currentTurnState == TurnState.CHARGING) {
            turnTimer.tick(deltaTime);

            if (turnTimer.isTimeOut()) {
                currentTurnState = TurnState.CHANGING_TURN;
                getActivePlayer().setAttacking(false);
            }
        }
        turnTimerPane.updateUI(turnTimer.getCurrentTimeInt(), currentPlayerTurn);


        if (currentTurnState == TurnState.CHARGING) {
            currentPower += POWER_CHARGE_RATE;
            if (currentPower > MAX_POWER) {
                currentPower = MAX_POWER;
            }
        }

        powerBarPane.update(getActivePlayer());

        if (currentTurnState == TurnState.PROJECTILE_IN_AIR && currentProjectile != null) {
            // Collision detection
            Character opponent = (currentPlayerTurn == PlayerTurn.PLAYER_ONE) ? onion : carrot;
            if (currentProjectile.getHitBox().intersects(opponent.getHitBox())) {
                opponent.takeDamage(20); // Example damage
                // TODO: Change opponent's image to a damaged one
                currentProjectile.setDestroyed(true);
                currentTurnState = TurnState.HIT;
                hitTimestamp = System.currentTimeMillis();
            }

            if (currentProjectile.isDestroyed()) {
                allObjects.remove(currentProjectile);
                currentProjectile = null;
                if (currentTurnState != TurnState.HIT) { // If it didn't hit anything, change turn
                    currentTurnState = TurnState.CHANGING_TURN;
                }
            }
        }

        if (currentTurnState == TurnState.HIT) {
            if (System.currentTimeMillis() - hitTimestamp > HIT_STATE_DURATION_MS) {
                // TODO: Change opponent's image back to normal
                currentTurnState = TurnState.CHANGING_TURN;
            }
        }

        if (currentTurnState == TurnState.CHANGING_TURN) {
            switchTurn();
            currentTurnState = TurnState.READY;
            currentPower = MIN_POWER;
        }
    }
    private void handleCollision(Character opponent) {
        opponent.takeDamage(20); // Example damage value
        // TODO: Trigger opponent's damaged animation/image
        currentProjectile.setDestroyed(true);
        currentTurnState = TurnState.HIT;
        hitTimestamp = System.currentTimeMillis();
    }

    private void switchTurn() {
        currentPlayerTurn = (currentPlayerTurn == PlayerTurn.PLAYER_ONE) ? PlayerTurn.PLAYER_TWO : PlayerTurn.PLAYER_ONE;
        updateCursor();
        turnTimer.reset();
    }

    public void checkGameOver() {
        if (carrot.isDead() || onion.isDead()) {
            isGameOver = true;
        }
    }

    public void showGameOverWindow() {
        String winner = "";
        if (carrot.isDead()) winner = "ONION";
        else if (onion.isDead()) winner = "CARROT";

        GameOverPane gameOverPane = new GameOverPane(winner);

        gameOverPane.setOnRestart(() -> {
            startGame();
        });

        gameOverPane.setOnExit(() -> {
            showMainMenu();
        });

        root.getChildren().add(gameOverPane);
    }

    private void updateCursor() {
        if (root.getScene() == null) return;

        if (currentPlayerTurn == PlayerTurn.PLAYER_ONE) {
            root.getScene().setCursor(carrotCursor);
        } else {
            root.getScene().setCursor(onionCursor);
        }
    }

    public Character getActivePlayer() {
        return (currentPlayerTurn == PlayerTurn.PLAYER_ONE) ? carrot : onion;
    }

    public TurnState getCurrentTurnState() {
        return currentTurnState;
    }

    public double getCurrentPower() {
        return currentPower;
    }

    public double getMaxPower() {
        return MAX_POWER;
    }
}
