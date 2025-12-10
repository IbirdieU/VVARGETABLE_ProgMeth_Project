package gui;

import entity.base.StatusEffect;
import entity.playerUnit.Carrot;
import entity.playerUnit.Onion;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PlayerStatusPane extends Pane {
    private final CustomHpBar onionHpBar;
    private final CustomHpBar carrotHpBar;
    private final Carrot carrot;
    private final Onion onion;
    private final HBox p1SkillBox;
    private final HBox p2SkillBox;
    private final VBox p1StatusBox;
    private final VBox p2StatusBox;

    public PlayerStatusPane(Carrot carrot, Onion onion) {
        super();
        this.carrot = carrot;
        this.onion = onion;
        p1SkillBox = new HBox(10);
        p2SkillBox = new HBox(10);

        p1SkillBox.setLayoutX(60);
        p1SkillBox.setLayoutY(90);

        p2SkillBox.setLayoutX(740);
        p2SkillBox.setLayoutY(90);

        p1StatusBox = new VBox(5);
        p1StatusBox.setAlignment(Pos.TOP_CENTER);
        p1StatusBox.setLayoutX(10);
        p1StatusBox.setLayoutY(300);

        p2StatusBox = new VBox(5);
        p2StatusBox.setAlignment(Pos.TOP_CENTER);
        p2StatusBox.setLayoutX(1210);
        p2StatusBox.setLayoutY(300);

        final double BAR_WIDTH = 480;
        final double BAR_HEIGHT = 20;

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
        double onionPercent = onion.getHp() / onion.getMaxHp();
        double carrotPercent = carrot.getHp() / carrot.getMaxHp();

        onionHpBar.setHp(onionPercent);
        carrotHpBar.setHp(carrotPercent);

        updateStatusIcons(p1StatusBox, carrot);
        updateStatusIcons(p2StatusBox, onion);
    }

    public void setSkills(SkillButton s1, SkillButton s2, SkillButton s3, SkillButton s4 , SkillButton s5, SkillButton s6) {
        p1SkillBox.getChildren().clear();
        p2SkillBox.getChildren().clear();

        p1SkillBox.getChildren().addAll(s1, s2, s3);
        p2SkillBox.getChildren().addAll(s4, s5, s6);
    }

    private void updateStatusIcons(VBox statusBox, entity.base.Character character) {
        statusBox.getChildren().clear();

        for (int i = character.getActiveStatusEffects().size() - 1; i >= 0; i--) {
            StatusEffect effect = character.getActiveStatusEffects().get(i);
            if (effect.getIcon() != null) {
                ImageView iconView = new ImageView(effect.getIcon());
                iconView.setFitWidth(50);
                iconView.setFitHeight(50);
                statusBox.getChildren().add(iconView);
            }
        }
    }
}
