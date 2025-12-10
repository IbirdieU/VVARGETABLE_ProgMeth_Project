package logic;

import entity.base.Character;
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
import logic.managers.*;
import logic.mechanics.PowerMechanic;

public class GameManager {
    public boolean isGameOver = false;
    private final StackPane root;
    private final SceneManager sceneManager;
    private final TurnManager turnManager;
    private final EntityManager entityManager;
    private final PowerMechanic powerMechanic;
    private final CollisionHandler collisionHandler;
    private SkillManager skillManager;

    private PlayerStatusPane playerStatusPane;
    private PowerBarPane powerBarPane;
    private final Canvas gameCanvas;

    private Projectile currentProjectile;

    private long hitTimestamp;

    private TurnInfoPane turnInfoPane;

    private final ImageCursor carrotCursor;
    private final ImageCursor onionCursor;

    private final InputHandler inputHandler;

    private boolean isDoomedMode = false;

    public GameManager(StackPane root) {
        this.root = root;
        this.sceneManager = new SceneManager(root, this);
        this.turnManager = new TurnManager();
        this.entityManager = new EntityManager();
        this.powerMechanic = new PowerMechanic();
        this.collisionHandler = new CollisionHandler(turnManager);
        this.gameCanvas = new Canvas(1280, 720);
        this.inputHandler = new InputHandler(this);
        double cursorSize = 64;
        Image carrotImg = new Image(getClass().getResourceAsStream("/unitImage/throwingCarrot.png"));
        Image onionImg = new Image(getClass().getResourceAsStream("/unitImage/throwingOnion.png"));
        double hotSpotX = cursorSize / 2;
        double hotSpotY = cursorSize / 2;
        this.carrotCursor = new ImageCursor(carrotImg, hotSpotX, hotSpotY);
        this.onionCursor = new ImageCursor(onionImg, hotSpotX, hotSpotY);
    }

    public void start() {
        sceneManager.showIntroMenu();
    }

    public void startGame() {
        turnManager.reset();
        entityManager.initializeGameWorld(isDoomedMode);

        String bgPath;
        if (isDoomedMode) {
            bgPath = "/background/doomedBackground.png";

        } else {
            bgPath = "/background/InGameBackgroundWall.png";
        }

        GamePane gamePane = new GamePane(gameCanvas, entityManager.getAllObjects(),bgPath);
        this.playerStatusPane = new PlayerStatusPane(entityManager.getCarrot(), entityManager.getOnion());
        this.powerBarPane = new PowerBarPane(this);
        this.turnInfoPane = new TurnInfoPane();

        sceneManager.showGameScene(gamePane, playerStatusPane, powerBarPane, turnInfoPane);

        this.skillManager = new SkillManager(this, playerStatusPane);
        skillManager.initializeSkills();

        setupInputHandlers();
        powerMechanic.reset();
        updateCursor();

        isGameOver = false;
        AnimationTimer gameLoop = new GameLoop(this, gamePane, playerStatusPane, entityManager.getAllObjects());
        gameLoop.start();
    }

    private void setupInputHandlers() {
        inputHandler.attachEventHandlers(root.getScene());
    }

    public void launchProjectile() {
        Character activePlayer = getActivePlayer();
        currentProjectile = activePlayer.shoot(powerMechanic.getCurrentPower(), turnManager.getWind(),this.isDoomedMode);
        entityManager.addProjectile(currentProjectile);
        skillManager.disableAllSkills();
        SoundManager.playThrowSound();
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
            long HIT_STATE_DURATION_MS = 1000;
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
