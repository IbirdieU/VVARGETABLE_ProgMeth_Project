package gui;

import entity.playerunit.Carrot;
import entity.playerunit.Onion;
import javafx.scene.layout.HBox;
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
    private HBox p1SkillBox;
    private HBox p2SkillBox;

    private final double BAR_WIDTH = 480;
    private final double BAR_HEIGHT = 20;

    public PlayerHpBarPane(Carrot carrot, Onion onion) {
        super();
        this.carrot = carrot;
        this.onion = onion;
        p1SkillBox = new HBox(10);
        p2SkillBox = new HBox(10);

        /// Skills
        p1SkillBox.setLayoutX(60);
        p1SkillBox.setLayoutY(90);

        p2SkillBox.setLayoutX(740);
        p2SkillBox.setLayoutY(90);

        // Onion (Player 2) HP Bar
        onionHpBarBackground = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.DARKGRAY);
        onionHpBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.LIMEGREEN);
        onionHpBarBackground.setArcWidth(BAR_HEIGHT);
        onionHpBarBackground.setArcHeight(BAR_HEIGHT);
        onionHpBar.setArcWidth(BAR_HEIGHT);
        onionHpBar.setArcHeight(BAR_HEIGHT);
        onionHpBarBackground.setX(740);

        onionHpBarBackground.setY(60);
        onionHpBar.setX(740);
        onionHpBar.setY(60);

        // Carrot (Player 1) HP Bar
        carrotHpBarBackground = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.DARKGRAY);
        carrotHpBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.LIMEGREEN);
        carrotHpBarBackground.setArcWidth(BAR_HEIGHT);
        carrotHpBarBackground.setArcHeight(BAR_HEIGHT);
        carrotHpBar.setArcWidth(BAR_HEIGHT);
        carrotHpBar.setArcHeight(BAR_HEIGHT);
        carrotHpBarBackground.setX(60);
        carrotHpBarBackground.setY(60);
        carrotHpBar.setX(60);
        carrotHpBar.setY(60);

        this.getChildren().addAll(onionHpBarBackground, onionHpBar, carrotHpBarBackground, carrotHpBar,p1SkillBox, p2SkillBox);
        this.setStyle("-fx-background-color: transparent;");

        update();
    }

    public void update() {
        double onionHpPercentage = (double) onion.getHp() / onion.getMaxHp();
        onionHpBar.setWidth(BAR_WIDTH * onionHpPercentage);

        double carrotHpPercentage = (double) carrot.getHp() / carrot.getMaxHp();
        carrotHpBar.setWidth(BAR_WIDTH * carrotHpPercentage);
    }

    public void setSkills(SkillButton s1, SkillButton s2, SkillButton s3, SkillButton s4) {
        p1SkillBox.getChildren().clear();
        p2SkillBox.getChildren().clear();

        p1SkillBox.getChildren().addAll(s1, s2);
        p2SkillBox.getChildren().addAll(s3, s4);
    }
}
