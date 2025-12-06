package logic;

import entity.base.GameObject;
import entity.playerunit.Carrot;
import entity.playerunit.Onion;
import gui.GamePane;
import gui.HowToMenu;
import gui.IntroMenu;
import gui.MainMenu;
import gui.PlayerHpBarPane;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GameManager {
    public ArrayList<GameObject> allObjects = new ArrayList<>();
    public boolean isGameOver = false;

    private StackPane root;
    private IntroMenu introMenu;
    private MainMenu mainMenu;
    private HowToMenu howToMenu;
    private GamePane gamePane;
    private PlayerHpBarPane playerHpBarPane;

    private Carrot carrot;
    private Onion onion;
    private AnimationTimer gameLoop;

    public GameManager(StackPane root) {
        this.root = root;
        this.introMenu = new IntroMenu();
        this.mainMenu = new MainMenu();
        this.howToMenu = new HowToMenu();

        // Setup event handlers
        introMenu.setOnAction(this::showMainMenu);
        mainMenu.setOnHowToAction(this::showHowToMenu);
        mainMenu.setOnStartAction(this::startGame);
        howToMenu.setOnStartAction(this::startGame);
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
        // Clear any previous game objects
        allObjects.clear();

        // Instantiate players
        this.carrot = new Carrot(980, 500, 100);
        this.onion = new Onion(200, 500, 100);
        allObjects.add(carrot);
        allObjects.add(onion);

        // Setup Panes
        this.gamePane = new GamePane(allObjects);
        this.playerHpBarPane = new PlayerHpBarPane(carrot, onion);
        root.getChildren().clear();
        root.getChildren().addAll(gamePane, playerHpBarPane);

        // Start Game Loop
        isGameOver = false;
        gameLoop = new GameLoop(this, gamePane, playerHpBarPane, allObjects);
        gameLoop.start();
    }

    public void checkGameOver() {
        if (carrot.isDead() || onion.isDead()) {
            isGameOver = true;
        }
    }
}
