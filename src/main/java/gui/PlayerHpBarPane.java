package gui;

import entity.base.StatusEffect;
import entity.playerunit.Carrot;
import entity.playerunit.Onion;
import javafx.scene.image.ImageView;
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
    private HBox p1StatusBox;
    private HBox p2StatusBox;

    private final double BAR_WIDTH = 480;
    private final double BAR_HEIGHT = 20;

    public PlayerHpBarPane(Carrot carrot, Onion onion) {
        super();
        this.carrot = carrot;
        this.onion = onion;
        p1SkillBox = new HBox(10);
        p2SkillBox = new HBox(10);

        // Skills
        p1SkillBox.setLayoutX(60);
        p1SkillBox.setLayoutY(90);

        p2SkillBox.setLayoutX(740);
        p2SkillBox.setLayoutY(90);

        // Status
        p1StatusBox = new HBox(5);
        p2StatusBox = new HBox(5);

        p1StatusBox.setLayoutX(60);
        p1StatusBox.setLayoutY(160);

        p2StatusBox.setLayoutX(740);
        p2StatusBox.setLayoutY(160);

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

        this.getChildren().addAll(onionHpBarBackground, onionHpBar, carrotHpBarBackground,
                carrotHpBar,p1SkillBox, p2SkillBox,p1StatusBox, p2StatusBox);
        this.setStyle("-fx-background-color: transparent;");

        //update();
    }

    public void update() {
        double onionHpPercentage = (double) onion.getHp() / onion.getMaxHp();
        onionHpBar.setWidth(BAR_WIDTH * onionHpPercentage);

        double carrotHpPercentage = (double) carrot.getHp() / carrot.getMaxHp();
        carrotHpBar.setWidth(BAR_WIDTH * carrotHpPercentage);

        updateStatusIcons(p1StatusBox, carrot);
        updateStatusIcons(p2StatusBox, onion);
    }

    public void setSkills(SkillButton s1, SkillButton s2, SkillButton s3, SkillButton s4) {
        p1SkillBox.getChildren().clear();
        p2SkillBox.getChildren().clear();

        p1SkillBox.getChildren().addAll(s1, s2);
        p2SkillBox.getChildren().addAll(s3, s4);
    }

    private void updateStatusIcons(HBox statusBox, entity.base.Character character) {
        statusBox.getChildren().clear();

        for (StatusEffect effect : character.getActiveStatusEffects()) {
            if (effect.getIcon() != null) {
                ImageView iconView = new ImageView(effect.getIcon());
                iconView.setFitWidth(50);
                iconView.setFitHeight(50);

                statusBox.getChildren().add(iconView);
            }
        }
    }
}
