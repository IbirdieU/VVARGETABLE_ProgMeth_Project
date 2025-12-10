package gui;

import entity.base.Character;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import logic.GameManager;
import logic.enums.TurnState;

public class PowerBarPane extends Pane {

    private GameManager gameManager;
    private Rectangle bg;
    private Rectangle fg;

    private final double BAR_WIDTH = 100;
    private final double BAR_HEIGHT = 15;
    private final double Y_OFFSET = 20;

    public PowerBarPane(GameManager gameManager) {
        this.gameManager = gameManager;


        bg = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        bg.setArcWidth(15);
        bg.setArcHeight(15);
        bg.setFill(Color.rgb(60, 60, 60));
        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(2);


        fg = new Rectangle(0, BAR_HEIGHT);
        fg.setArcWidth(15);
        fg.setArcHeight(15);
        fg.setFill(
                new LinearGradient(
                        0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(255, 120, 100)),
                        new Stop(1, Color.rgb(255, 60, 60))
                )
        );


        DropShadow shadow = new DropShadow(8, Color.gray(0, 0.4));
        bg.setEffect(shadow);

        getChildren().addAll(bg, fg);

        setVisible(false);
    }

    public void update(Character activePlayer) {
        TurnState current = gameManager.getCurrentTurnState();

        if (current == TurnState.CHARGING) {
            setVisible(true);

            double x = activePlayer.getX() + activePlayer.getWidth() / 2 - BAR_WIDTH / 2;
            double y = activePlayer.getY() - Y_OFFSET;
            setTranslateX(x);
            setTranslateY(y);

            double percent = gameManager.getCurrentPower() / gameManager.getMaxPower();
            fg.setWidth(BAR_WIDTH * percent);

        } else {
            setVisible(false);
        }
    }
}
