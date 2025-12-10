package logic;

import entity.base.GameObject;
import gui.GamePane;
import gui.PlayerStatusPane;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class GameLoop extends AnimationTimer {

    private final GameManager gameManager;
    private final GamePane gamePane;
    private final PlayerStatusPane playerStatusPane;
    private final ArrayList<GameObject> allObjects;
    private long lastNanoTime = System.nanoTime();

    public GameLoop(GameManager gameManager, GamePane gamePane, PlayerStatusPane playerStatusPane, ArrayList<GameObject> allObjects) {
        this.gameManager = gameManager;
        this.gamePane = gamePane;
        this.playerStatusPane = playerStatusPane;
        this.allObjects = allObjects;
    }

    @Override
    public void handle(long currentNanoTime) {
        if (gameManager.isGameOver) {
            this.stop();
            System.out.println("Game Over!");
            gameManager.showGameOverWindow();
            return;
        }

        double deltaTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
        lastNanoTime = currentNanoTime;

        // GameManager now handles updating the power bar

        for (GameObject gameObject : allObjects) {
            gameObject.update(deltaTime);
        }

        gameManager.update(deltaTime);
        playerStatusPane.update();
        gamePane.drawObjects();
        gameManager.checkGameOver();
    }
}
