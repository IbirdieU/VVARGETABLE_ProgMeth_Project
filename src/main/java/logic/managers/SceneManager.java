package logic.managers;

import gui.*;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import logic.GameManager;
import logic.enums.PlayerTurn;

public class SceneManager {

    private final StackPane root;
    private final GameManager gameManager;

    private IntroMenu introMenu;
    private MainMenu mainMenu;
    private HowToMenu howToMenu;
    private ModeSelectMenu modeSelectMenu;

    private ImageCursor carrotCursor;
    private ImageCursor onionCursor;

    public SceneManager(StackPane root, GameManager gameManager) {
        this.root = root;
        this.gameManager = gameManager;
        initializeMenus();
        initializeCursors();
    }

    private void initializeMenus() {
        this.introMenu = new IntroMenu();
        this.mainMenu = new MainMenu();
        this.howToMenu = new HowToMenu();
        this.modeSelectMenu = new ModeSelectMenu();

        introMenu.setOnAction(this::showMainMenu);
        mainMenu.setOnHowToAction(this::showHowToMenu);
        mainMenu.setOnStartAction(this::showModeSelectMenu);
        howToMenu.setOnStartAction(this::showModeSelectMenu);
        modeSelectMenu.setOnClassicAction(() -> {
            gameManager.setDoomedMode(false);
            gameManager.startGame();
        });
        modeSelectMenu.setOnDoomedAction(() -> {
            gameManager.setDoomedMode(true);
            gameManager.startGame();
        });
    }

    private void initializeCursors() {
        double cursorSize = 64;
        Image carrotImg = new Image(getClass().getResourceAsStream("/unitImage/throwingCarrot.png"));
        Image onionImg = new Image(getClass().getResourceAsStream("/unitImage/throwingOnion.png"));
        double hotSpotX = cursorSize / 2;
        double hotSpotY = cursorSize / 2;
        this.carrotCursor = new ImageCursor(carrotImg, hotSpotX, hotSpotY);
        this.onionCursor = new ImageCursor(onionImg, hotSpotX, hotSpotY);
    }

    public void showIntroMenu() {
        root.getChildren().clear();
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

    public void showGameScene(GamePane gamePane, PlayerStatusPane playerStatusPane, PowerBarPane powerBarPane, TurnInfoPane turnInfoPane) {
        root.getChildren().clear();
        root.getChildren().addAll(gamePane, playerStatusPane, powerBarPane, turnInfoPane);
    }

    public void showGameOverWindow(String winner) {
        GameOverPane gameOverPane = new GameOverPane(winner);

        gameOverPane.setOnRestart(() -> gameManager.startGame());
        gameOverPane.setOnExit(this::showMainMenu);

        root.getChildren().add(gameOverPane);
    }

    public void updateCursor(PlayerTurn turn) {
        if (root.getScene() == null) return;
        root.getScene().setCursor((turn == PlayerTurn.PLAYER_ONE) ? carrotCursor : onionCursor);
    }
}
