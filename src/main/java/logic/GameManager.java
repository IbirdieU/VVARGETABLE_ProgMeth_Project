package logic;

import entity.base.GameObject;
import entity.playerunit.Carrot;
import entity.playerunit.Onion;
import gui.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GameManager {
    public ArrayList<GameObject> allObjects = new ArrayList<>();
    private Onion onion;
    private Carrot carrot;
    public boolean isGameOver = false;
    private final StackPane root;
    private final IntroMenu introMenu;
    private final MainMenu mainMenu;
    private final HowToMenu howToMenu;
    private final Canvas gameCanvas;
    public GamePane gamePane;
    public PlayerHpBarPane  playerHpBarPane;
    public GameManager(StackPane root) {
        this.root = root;
        this.introMenu = new IntroMenu();
        this.mainMenu = new MainMenu();
        this.howToMenu = new HowToMenu();
        this.gameCanvas = new Canvas(800, 600);

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
        root.getChildren().clear();
        root.getChildren().add(gameCanvas);
        carrot = new Carrot(200, 100,100);
        onion = new Onion(1040,100,100);
        allObjects.add(carrot);
        allObjects.add(onion);
        gamePane = new GamePane(gameCanvas,allObjects);
        playerHpBarPane = new PlayerHpBarPane(this.carrot,this.onion);
        gamePane.getChildren().add(playerHpBarPane);
        root.getChildren().clear();
        root.getChildren().add(gamePane);
        // Initialize and start the game logic here
    }

    public Carrot getCarrot() {
        return carrot;
    }

    public Onion getOnion() {
        return onion;
    }

    public boolean isGameOver() {
        return carrot.isDead() || onion.isDead();
    }

}
