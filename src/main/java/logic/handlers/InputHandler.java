package logic.handlers;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import logic.GameManager;
import logic.enums.TurnState;

public class InputHandler {

    private final GameManager gameManager;

    public InputHandler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void attachEventHandlers(Scene scene) {
        scene.setOnMousePressed(e -> handleMousePressed(e.getButton(), e.getX(), e.getY()));
        scene.setOnMouseReleased(e -> handleMouseReleased(e.getButton()));
    }

    public void handleMousePressed(MouseButton button, double x, double y) {
        if (button == MouseButton.PRIMARY && gameManager.getCurrentTurnState() == TurnState.READY) {
            if (gameManager.getActivePlayer().getHitBox().contains(x, y)) {
                gameManager.setCurrentTurnState(TurnState.CHARGING);
                gameManager.getPowerMechanic().reset();
                gameManager.getActivePlayer().setAttacking(true);
            }
        }
    }

    public void handleMouseReleased(MouseButton button) {
        if (button == MouseButton.PRIMARY && gameManager.getCurrentTurnState() == TurnState.CHARGING) {
            gameManager.getActivePlayer().setAttacking(false);
            gameManager.launchProjectile();
            gameManager.setCurrentTurnState(TurnState.PROJECTILE_IN_AIR);
        }
    }
}
