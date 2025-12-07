package gui;

import entity.base.Character;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.GameManager;
import logic.TurnState;

public class PowerBarPane extends Pane {

    private GameManager gameManager;
    private Rectangle powerBarBackground;
    private Rectangle powerBarForeground;

    private final double BAR_WIDTH = 100;
    private final double BAR_HEIGHT = 15;
    private final double Y_OFFSET = 20; // How far above the player the bar should be

    public PowerBarPane(GameManager gameManager) {
        this.gameManager = gameManager;

        powerBarBackground = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.DARKGRAY);
        powerBarForeground = new Rectangle(0, BAR_HEIGHT, Color.RED);
        powerBarBackground.setStroke(Color.BLACK);

        getChildren().addAll(powerBarBackground, powerBarForeground);
        setVisible(false); // Initially hidden
    }

    public void update(Character activePlayer) {
        TurnState currentState = gameManager.getCurrentTurnState();

        // Only show the bar when the player is actively charging power
        if (currentState == TurnState.CHARGING) {
            setVisible(true);

            // Calculate position above the player
            double newX = activePlayer.getX() + (activePlayer.getWidth() / 2) - (BAR_WIDTH / 2);
            double newY = activePlayer.getY() - Y_OFFSET;
            setTranslateX(newX);
            setTranslateY(newY);

            // Update power bar width
            double powerPercentage = gameManager.getCurrentPower() / gameManager.getMaxPower();
            powerBarForeground.setWidth(BAR_WIDTH * powerPercentage);
        } else {
            // Hide the bar during all other states (READY, PROJECTILE_IN_AIR, etc.)
            setVisible(false);
        }
    }
}
