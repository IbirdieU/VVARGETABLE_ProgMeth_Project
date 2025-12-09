package logic;

import entity.base.GameObject;
import gui.GamePane;
import gui.PlayerHpBarPane;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class GameLoop extends AnimationTimer {

    private final GameManager gameManager;
    private final GamePane gamePane;
    private final PlayerHpBarPane playerHpBarPane;
    private final ArrayList<GameObject> allObjects;
    private long lastNanoTime = System.nanoTime();

    public GameLoop(GameManager gameManager, GamePane gamePane, PlayerHpBarPane playerHpBarPane, ArrayList<GameObject> allObjects) {
        this.gameManager = gameManager;
        this.gamePane = gamePane;
        this.playerHpBarPane = playerHpBarPane;
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
        playerHpBarPane.update();
        gamePane.drawObjects();
        gameManager.checkGameOver();
    }
}
