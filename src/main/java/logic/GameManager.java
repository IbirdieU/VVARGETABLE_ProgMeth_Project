package logic;

import entity.base.Character;
import entity.playerunit.Carrot;
import entity.playerunit.Onion;
import entity.unit.Projectile;
import gui.*;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.enums.PlayerTurn;
import logic.enums.TurnState;
import logic.handlers.CollisionHandler;
import logic.handlers.InputHandler;
import logic.managers.EntityManager;
import logic.managers.SceneManager;
import logic.managers.SkillManager;
import logic.managers.TurnManager;
import logic.mechanics.PowerMechanic;

public class GameManager {
    public boolean isGameOver = false;
    private StackPane root;
    private SceneManager sceneManager;
    private TurnManager turnManager;
    private EntityManager entityManager;
    private PowerMechanic powerMechanic;
    private CollisionHandler collisionHandler;
    private SkillManager skillManager;

    private GamePane gamePane;
    private PlayerStatusPane playerStatusPane;
    private PowerBarPane powerBarPane;
    private Canvas gameCanvas;

    private Projectile currentProjectile;
    private AnimationTimer gameLoop;

    private long hitTimestamp;
    private final long HIT_STATE_DURATION_MS = 1000;

    private TurnInfoPane turnInfoPane;

    private ImageCursor carrotCursor;
    private ImageCursor onionCursor;

    private InputHandler inputHandler;

    private boolean isDoomedMode = false;

    public GameManager(StackPane root) {
        this.root = root;
        this.sceneManager = new SceneManager(root, this);
        this.turnManager = new TurnManager();
        this.entityManager = new EntityManager();
        this.powerMechanic = new PowerMechanic();
        this.collisionHandler = new CollisionHandler(turnManager);
        this.gameCanvas = new Canvas(1280, 720);

        double cursorSize = 64;
        Image carrotImg = new Image("/unitImage/throwingCarrot.png");
        Image onionImg = new Image("/unitImage/throwingOnion.png");
        double hotSpotX = cursorSize / 2;
        double hotSpotY = cursorSize / 2;
        this.carrotCursor = new ImageCursor(carrotImg, hotSpotX, hotSpotY);
        this.onionCursor = new ImageCursor(onionImg, hotSpotX, hotSpotY);
    }

    public void start() {
        sceneManager.showIntroMenu();
    }

    public void startGame() {
        entityManager.initializeGameWorld();

        String bgPath;
        if (isDoomedMode) {
            bgPath = "/background/doomedBackground.png";

        } else {
            bgPath = "background/inGameBackgroundWall.png";
        }

        this.gamePane = new GamePane(gameCanvas, entityManager.getAllObjects(),bgPath);
        this.playerStatusPane = new PlayerStatusPane(entityManager.getCarrot(), entityManager.getOnion());
        this.powerBarPane = new PowerBarPane(this);

        this.inputHandler = new InputHandler(this);
        this.turnInfoPane = new TurnInfoPane();

        sceneManager.showGameScene(gamePane, playerStatusPane, powerBarPane, turnInfoPane);

        this.skillManager = new SkillManager(this, playerStatusPane);
        skillManager.initializeSkills();

        setupInputHandlers();
        powerMechanic.reset();
        updateCursor();

        isGameOver = false;
        gameLoop = new GameLoop(this, gamePane, playerStatusPane, entityManager.getAllObjects());
        gameLoop.start();
    }

    private void setupInputHandlers() {
        root.getScene().setOnMousePressed(e -> inputHandler.handleMousePressed(e.getButton(), e.getX(), e.getY()));
        root.getScene().setOnMouseReleased(e -> inputHandler.handleMouseReleased(e.getButton()));
    }

    public void launchProjectile() {
        Character activePlayer = getActivePlayer();
        double startX, startY, angle, scale = activePlayer.getProjectileScale();

        Image projectileImage = (activePlayer instanceof Onion)
                ? ((Onion) activePlayer).getProjectileImage()
                : ((Carrot) activePlayer).getProjectileImage();

        double realWidth = projectileImage.getWidth() * 0.2 * scale;
        double realHeight = projectileImage.getHeight() * 0.2 * scale;
        double spawnCenterX = activePlayer.getX() + (activePlayer.getWidth() / 2);
        double spawnCenterY = activePlayer.getY() + (activePlayer.getHeight() / 2);

        startX = spawnCenterX - (realWidth / 2);
        startY = spawnCenterY - (realHeight / 2);

        if (turnManager.getCurrentPlayerTurn() == PlayerTurn.PLAYER_ONE) {
            startX += 20;
            angle = -60;
        } else {
            startX -= 20;
            angle = -120;
        }

        double dmgMult = activePlayer.getDamageMultiplier();
        boolean isPoison = activePlayer.isPoisonShot();
        boolean isStun = activePlayer.isStunShot();
        double modeDamageFactor = isDoomedMode ? 0.6 : 0.3;
        double totalDamage = powerMechanic.getCurrentPower() * modeDamageFactor * dmgMult;

        currentProjectile = new Projectile(startX, startY, projectileImage, angle, powerMechanic.getCurrentPower(), totalDamage, scale, isPoison, turnManager.getWind(),isStun);
        entityManager.addProjectile(currentProjectile);

        activePlayer.resetBuffs();

        skillManager.disableAllSkills();
    }

    public void update(double deltaTime) {
        if (isGameOver) return;

        turnManager.update(deltaTime);
        if (turnManager.getCurrentTurnState() == TurnState.CHANGING_TURN) {
            getActivePlayer().setAttacking(false);
        }

        turnInfoPane.updateUI(turnManager.getTurnTimer().getCurrentTimeInt(), turnManager.getCurrentPlayerTurn(), turnManager.getWind());

        if (turnManager.getCurrentTurnState() == TurnState.CHARGING) {
            powerMechanic.charge(deltaTime);
        }

        powerBarPane.update(getActivePlayer());
        playerStatusPane.update();

        if (turnManager.getCurrentTurnState() == TurnState.PROJECTILE_IN_AIR && currentProjectile != null) {
            Character opponent = (turnManager.getCurrentPlayerTurn() == PlayerTurn.PLAYER_ONE) ? entityManager.getOnion() : entityManager.getCarrot();
            long hitTime = collisionHandler.handleCollision(currentProjectile, opponent, entityManager.getWall());
            if (hitTime > 0) {
                this.hitTimestamp = hitTime;
            }
            entityManager.cleanupDestroyed(currentProjectile);
            if (currentProjectile.isDestroyed()) {
                currentProjectile = null;
            }
        }

        if (turnManager.getCurrentTurnState() == TurnState.HIT) {
            if (System.currentTimeMillis() - hitTimestamp > HIT_STATE_DURATION_MS) {
                turnManager.setCurrentTurnState(TurnState.CHANGING_TURN);
            }
        }

        if (turnManager.getCurrentTurnState() == TurnState.CHANGING_TURN) {
            switchTurn();
            powerMechanic.reset();
        }
    }

    private void switchTurn() {
        turnManager.switchTurn();
        boolean shouldSkip = getActivePlayer().checkTurnStatus();
        if (shouldSkip) {
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

                turnManager.forceEndTurn();
            });

            pause.play();

        }
        updateCursor();

        skillManager.updateSkillButtons(turnManager.getCurrentPlayerTurn(),shouldSkip);
    }

    public void checkGameOver() {
        if (entityManager.getCarrot().isDead() || entityManager.getOnion().isDead()) {
            isGameOver = true;
        }
    }

    public void showGameOverWindow() {
        String winner = (entityManager.getCarrot().isDead()) ? "ONION" : "CARROT";
        sceneManager.showGameOverWindow(winner);
    }

    private void updateCursor() {
        if (root.getScene() == null) return;
        root.getScene().setCursor((turnManager.getCurrentPlayerTurn() == PlayerTurn.PLAYER_ONE) ? carrotCursor : onionCursor);
    }

    public void setDoomedMode(boolean isDoomedMode) {
        this.isDoomedMode = isDoomedMode;
    }

    public Character getActivePlayer() {
        return (turnManager.getCurrentPlayerTurn() == PlayerTurn.PLAYER_ONE) ? entityManager.getCarrot() : entityManager.getOnion();
    }

    public TurnState getCurrentTurnState() {
        return turnManager.getCurrentTurnState();
    }

    public double getCurrentPower() {
        return powerMechanic.getCurrentPower();
    }

    public double getMaxPower() {
        return powerMechanic.getMaxPower();
    }

    public double getMinPower() {
        return powerMechanic.getMinPower();
    }

    public void setCurrentTurnState(TurnState currentTurnState) {
        turnManager.setCurrentTurnState(currentTurnState);
    }
    
    public TurnManager getTurnManager() {
        return turnManager;
    }

    public PowerMechanic getPowerMechanic() {
        return powerMechanic;
    }

    public boolean isDoomedMode() {
        return isDoomedMode;
    }

    public void handleSkillUsage() {
        skillManager.handleSkillUsage(turnManager.getCurrentPlayerTurn());
    }
}
