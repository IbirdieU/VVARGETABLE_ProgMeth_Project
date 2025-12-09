package logic;

import entity.base.Character;
import entity.base.GameObject;
import entity.playerSkill.*;
import entity.playerSkill.Double;
import entity.playerunit.Carrot;
import entity.playerunit.Onion;
import entity.status.StunStatus;
import entity.status.ToxicStatus;
import entity.unit.Projectile;
import entity.unit.Wall;
import gui.*;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    private ModeSelectMenu modeSelectMenu;
    private GamePane gamePane;
    private PlayerHpBarPane playerHpBarPane;
    private PowerBarPane powerBarPane;
    private Canvas gameCanvas;

    private Carrot carrot;
    private Onion onion;
    private Wall wall;
    private Projectile currentProjectile;
    private AnimationTimer gameLoop;


    private double currentPower;
    private final double MAX_POWER = 100;
    private final double MIN_POWER = 10;
    private final double POWER_CHARGE_RATE = 120;

    private long hitTimestamp;
    private final long HIT_STATE_DURATION_MS = 1000; // 1 second

    private TurnTimer turnTimer;
    private TurnTimerPane turnTimerPane;

    private ImageCursor carrotCursor;
    private ImageCursor onionCursor;

    private SkillButton p1Skill1, p1Skill2,p1Skill3;
    private SkillButton p2Skill1, p2Skill2,p2Skill3;

    private Wind wind;

    private boolean isDoomedMode = false;

    public GameManager(StackPane root) {
        this.root = root;
        this.introMenu = new IntroMenu();
        this.mainMenu = new MainMenu();
        this.howToMenu = new HowToMenu();
        this.modeSelectMenu = new ModeSelectMenu();
        this.gameCanvas = new Canvas(1280, 720);

        introMenu.setOnAction(this::showMainMenu);
        mainMenu.setOnHowToAction(this::showHowToMenu);
        mainMenu.setOnStartAction(this::showModeSelectMenu);
        howToMenu.setOnStartAction(this::showModeSelectMenu);
        modeSelectMenu.setOnClassicAction(() -> {
            this.isDoomedMode = false;
            startGame();
        });

        modeSelectMenu.setOnDoomedAction(() -> {
            this.isDoomedMode = true;
            startGame();
        });

        double cursorSize = 64;

        Image carrotImg = new Image("/unitImage/throwingCarrot.png");
        Image onionImg = new Image("/unitImage/throwingOnion.png");

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

    private  void showModeSelectMenu(){
        root.getChildren().clear();
        root.getChildren().add(modeSelectMenu);
    }

    private void startGame() {
        allObjects.clear();

        final double GROUND_Y = 660;

        this.carrot = new Carrot(20, 0, 100);
        this.onion = new Onion(1050, 0, 100);
        // Create the invisible wall hitbox. Adjust x, y, width, and height to match your background.
        this.wall = new Wall(600, 327, 76, 393);
        this.carrot.setY(GROUND_Y - this.carrot.getHeight());
        this.onion.setY(GROUND_Y - this.onion.getHeight());
        allObjects.add(carrot);
        allObjects.add(onion);
        allObjects.add(wall);

        this.gamePane = new GamePane(gameCanvas, allObjects);
        this.playerHpBarPane = new PlayerHpBarPane(carrot, onion);
        this.powerBarPane = new PowerBarPane(this);

        this.wind = new Wind();
        
        

        /// TurnTimer
        this.turnTimer = new TurnTimer(10.0);

        this.turnTimerPane = new TurnTimerPane();

        root.getChildren().clear();
        root.getChildren().addAll(gamePane, playerHpBarPane, powerBarPane,turnTimerPane);

        setupInputHandlers();

        currentPlayerTurn = PlayerTurn.PLAYER_ONE;
        currentTurnState = TurnState.READY;
        currentPower = MIN_POWER;

        updateCursor();

        /// Skills
        Skill skillC1 = new Double();
        Skill skillC2 = new Heal();
        Skill skillC3 = new Stun();
        Skill skillO1 = new Toxic();
        Skill skillO2 = new Growth();
        Skill skillO3 = new Shield();

        p1Skill1 = new SkillButton(skillC1, this);
        p1Skill2 = new SkillButton(skillC2, this);
        p1Skill3 = new SkillButton(skillC3, this);

        p2Skill1 = new SkillButton(skillO1, this);
        p2Skill2 = new SkillButton(skillO2, this);
        p2Skill3 = new SkillButton(skillO3,this);

        p1Skill1.setTurnActive(true);
        p1Skill2.setTurnActive(true);
        p1Skill3.setTurnActive(true);

        p2Skill1.setTurnActive(false);
        p2Skill2.setTurnActive(false);
        p2Skill3.setTurnActive(false);

        //this.playerHpBarPane = new PlayerHpBarPane(carrot, onion);
        this.playerHpBarPane.setSkills(p1Skill1, p1Skill2,p1Skill3, p2Skill1, p2Skill2,p2Skill3);

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
        double startY ;
        double angle;
        double scale = activePlayer.getProjectileScale();

        Image projectileImage = (activePlayer instanceof Onion) ?
                ((Onion) activePlayer).getProjectileImage() :
                ((Carrot) activePlayer).getProjectileImage();

        //Start Position
        double realWidth = projectileImage.getWidth() * 0.2 * scale;
        double realHeight = projectileImage.getHeight() * 0.2 * scale;

        double spawnCenterX = activePlayer.getX() + (activePlayer.getWidth() / 2);
        double spawnCenterY = activePlayer.getY() + (activePlayer.getHeight() / 2);


        startX = spawnCenterX - (realWidth / 2);
        startY = spawnCenterY - (realHeight / 2);

        if (currentPlayerTurn == PlayerTurn.PLAYER_ONE) {
            startX += 20;
        } else {
            startX -= 20;
        }

        // Firing position logic is now swapped
        if (currentPlayerTurn == PlayerTurn.PLAYER_ONE) {
            startX = activePlayer.getX() + 50;
            angle = -60;
        } else {
            startX = activePlayer.getX() + 65;
            angle = -120;
        }

        // Adjust startX to account for the projectile's own width

        /// Skill
        double dmgMult = activePlayer.getDamageMultiplier();
        boolean isPoison = activePlayer.isPoisonShot();
        boolean isStun = activePlayer.isStunShot();

        //set Doomed Mode
        double modeDamageFactor = isDoomedMode ? 0.6 : 0.3;

        //set starter damage * dmgMult
        double totalDamage = currentPower * modeDamageFactor * dmgMult;

        currentProjectile = new Projectile(startX, startY, projectileImage, angle, currentPower,totalDamage,scale,
                isPoison, wind,isStun);
        allObjects.add(currentProjectile);

        activePlayer.resetBuffs();

        /// ฺSkill-used-after-launched Blocking
        if (currentPlayerTurn == PlayerTurn.PLAYER_ONE) {
            p1Skill1.setTurnActive(false);
            p1Skill2.setTurnActive(false);
            p1Skill3.setTurnActive(false);
        } else {
            p2Skill1.setTurnActive(false);
            p2Skill2.setTurnActive(false);
            p2Skill3.setTurnActive(false);
        }
    }

    public void forceEndTurn() {
        this.currentTurnState = TurnState.CHANGING_TURN;
    }

    public void update(double deltaTime) {
        if (isGameOver) return;

        if (currentTurnState == TurnState.READY || currentTurnState == TurnState.CHARGING) {
            turnTimer.tick(deltaTime);

            if (turnTimer.isTimeOut()) {
                currentTurnState = TurnState.CHANGING_TURN;
                getActivePlayer().setAttacking(false);
            }
        }
        turnTimerPane.updateUI(turnTimer.getCurrentTimeInt(), currentPlayerTurn, wind);


        if (currentTurnState == TurnState.CHARGING) {
            currentPower += POWER_CHARGE_RATE * deltaTime;
            if (currentPower > MAX_POWER) {
                currentPower = MAX_POWER;
            }
        }

        powerBarPane.update(getActivePlayer());

        playerHpBarPane.update();

        if (currentTurnState == TurnState.PROJECTILE_IN_AIR && currentProjectile != null) {
            // Collision detection
            Character opponent = (currentPlayerTurn == PlayerTurn.PLAYER_ONE) ? onion : carrot;
            if (currentProjectile.getHitBox().intersects(opponent.getHitBox())) {
                opponent.takeDamage(currentProjectile.getDamage()); // Example damage
                //opponent.takeDamage(20);

                //Toxic
                if (currentProjectile.isPoisonous()) {
                    opponent.addStatusEffect(new ToxicStatus(
                            currentProjectile.getPoisonDuration(),
                            currentProjectile.getPoisonDamage()
                    ));
                }

                //Stun
                if (currentProjectile.isStun()) {
                    opponent.addStatusEffect(new StunStatus(2));
                }

                // TODO: Change opponent's image to a damaged one
                currentProjectile.setDestroyed(true);
                currentTurnState = TurnState.HIT;
                hitTimestamp = System.currentTimeMillis();
            } else if (currentProjectile.getHitBox().intersects(wall.getHitBox())) {
                currentProjectile.setDestroyed(true);
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
        wind.randomize();
        currentPlayerTurn = (currentPlayerTurn == PlayerTurn.PLAYER_ONE) ? PlayerTurn.PLAYER_TWO : PlayerTurn.PLAYER_ONE;
        //getActivePlayer().checkTurnStatus();
        updateCursor();
        turnTimer.reset();

        //Stun
        boolean isStunned = getActivePlayer().checkTurnStatus();
        if (isStunned) {
            System.out.println("Player is stunned! Skipping turn...");

            Text stunText = new Text("STUNNED! (SKIP TURN)");
            stunText.setFont(Font.font("Verdana", 50));
            stunText.setFill(Color.WHITE);
            stunText.setStroke(Color.BLACK);
            stunText.setStrokeWidth(2);

            root.getChildren().add(stunText);

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));

            pause.setOnFinished(e -> {
                root.getChildren().remove(stunText);

                switchTurn();
            });

            pause.play();
            return;
        }

        /// Skill cooldown

        //Doomed Mode
        int cooldownReductionAmount = isDoomedMode ? 5 : 1;
        if (currentPlayerTurn == PlayerTurn.PLAYER_ONE) {
            for (int i = 0; i < cooldownReductionAmount; i++) {
                p1Skill1.onTurnStart();
                p1Skill2.onTurnStart();
                p1Skill3.onTurnStart();
            }

            p2Skill1.setTurnActive(false);
            p2Skill2.setTurnActive(false);
            p2Skill3.setTurnActive(false);

        } else {
            for (int i = 0; i < cooldownReductionAmount; i++) {
                p2Skill1.onTurnStart();
                p2Skill2.onTurnStart();
                p2Skill3.onTurnStart();
            }

            p1Skill1.setTurnActive(false);
            p1Skill2.setTurnActive(false);
            p1Skill3.setTurnActive(false);
        }
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

    public Wind getWind() {
        return wind;
    }
}
