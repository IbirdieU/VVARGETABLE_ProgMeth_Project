package gui;

import entity.base.StatusEffect;
import entity.playerunit.Carrot;
import entity.playerunit.Onion;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.skills.Skill;

public class PlayerStatusPane extends Pane {
    private CustomHpBar onionHpBar;
    private CustomHpBar carrotHpBar;
    private Carrot carrot;
    private Onion onion;
    private HBox p1SkillBox;
    private HBox p2SkillBox;
    private VBox p1StatusBox;
    private VBox p2StatusBox;

    private final double BAR_WIDTH = 480;
    private final double BAR_HEIGHT = 20;

    public PlayerStatusPane(Carrot carrot, Onion onion) {
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

        // Status
        p1StatusBox = new VBox(5);
        p2StatusBox = new VBox(5);

        p1StatusBox.setLayoutX(0);
        p1StatusBox.setLayoutY(300);

        p2StatusBox.setLayoutX(1210);
        p2StatusBox.setLayoutY(300);

        onionHpBar = new CustomHpBar(BAR_WIDTH, BAR_HEIGHT);
        onionHpBar.setLayoutX(740);
        onionHpBar.setLayoutY(60);
        onionHpBar.setRightSide();

        carrotHpBar = new CustomHpBar(BAR_WIDTH, BAR_HEIGHT);
        carrotHpBar.setLayoutX(60);
        carrotHpBar.setLayoutY(60);

        this.getChildren().addAll(onionHpBar, carrotHpBar,
                p1SkillBox, p2SkillBox,
                p1StatusBox, p2StatusBox);


        this.setStyle("-fx-background-color: transparent;");

        update();
    }

    public void update() {
        double onionPercent = (double) onion.getHp() / onion.getMaxHp();
        double carrotPercent = (double) carrot.getHp() / carrot.getMaxHp();

        onionHpBar.setHp(onionPercent);
        carrotHpBar.setHp(carrotPercent);

        updateStatusIcons(p1StatusBox, carrot);
        updateStatusIcons(p2StatusBox, onion);
    }

    public void setSkills(SkillButton c1, SkillButton c2, SkillButton c3, SkillButton o1,SkillButton o2, SkillButton o3) {
        p1SkillBox.getChildren().clear();
        p2SkillBox.getChildren().clear();

        p1SkillBox.getChildren().addAll(c1, c2,c3);
        p2SkillBox.getChildren().addAll(o1, o2,o3);
    }

    private void updateStatusIcons(VBox statusBox, entity.base.Character character) {
        statusBox.getChildren().clear();

        for (StatusEffect effect : character.getActiveStatusEffects()) {
            if (effect.getIcon() != null) {
                ImageView iconView = new ImageView(effect.getIcon());
                iconView.setFitWidth(70);
                iconView.setFitHeight(70);

                statusBox.getChildren().add(iconView);
            }
        }
    }
}
