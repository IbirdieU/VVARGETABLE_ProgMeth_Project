package gui;

import entity.playerunit.Carrot;
import entity.playerunit.Onion;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerHpBarPane extends Pane {
    private Rectangle onionHpBarBackground;
    private Rectangle carrotHpBarBackground;
    private Rectangle onionHpBar;
    private Rectangle carrotHpBar;
    private Carrot carrot;
    private Onion onion;

    private final double BAR_WIDTH = 480;
    private final double BAR_HEIGHT = 20;

    public PlayerHpBarPane(Carrot carrot, Onion onion) {
        super();
        this.carrot = carrot;
        this.onion = onion;

        // Onion (Player 1) HP Bar
        onionHpBarBackground = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.DARKGRAY);
        onionHpBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.LIMEGREEN);
        onionHpBarBackground.setArcWidth(BAR_HEIGHT);
        onionHpBarBackground.setArcHeight(BAR_HEIGHT);
        onionHpBar.setArcWidth(BAR_HEIGHT);
        onionHpBar.setArcHeight(BAR_HEIGHT);
        onionHpBarBackground.setX(60);
        onionHpBarBackground.setY(60);
        onionHpBar.setX(60);
        onionHpBar.setY(60);

        // Carrot (Player 2) HP Bar
        carrotHpBarBackground = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.DARKGRAY);
        carrotHpBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.LIMEGREEN);
        carrotHpBarBackground.setArcWidth(BAR_HEIGHT);
        carrotHpBarBackground.setArcHeight(BAR_HEIGHT);
        carrotHpBar.setArcWidth(BAR_HEIGHT);
        carrotHpBar.setArcHeight(BAR_HEIGHT);
        carrotHpBarBackground.setX(740);
        carrotHpBarBackground.setY(60);
        carrotHpBar.setX(740);
        carrotHpBar.setY(60);

        this.getChildren().addAll(onionHpBarBackground, onionHpBar, carrotHpBarBackground, carrotHpBar);
        this.setStyle("-fx-background-color: transparent;");

        update();
    }

    public void update() {
        double onionHpPercentage = (double) onion.getHp() / onion.getMaxHp();
        onionHpBar.setWidth(BAR_WIDTH * onionHpPercentage);

        double carrotHpPercentage = (double) carrot.getHp() / carrot.getMaxHp();
        carrotHpBar.setWidth(BAR_WIDTH * carrotHpPercentage);
    }
}
