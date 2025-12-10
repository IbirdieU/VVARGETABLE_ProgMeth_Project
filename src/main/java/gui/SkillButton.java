package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.GameManager;
import logic.skills.Skill;

public class SkillButton extends StackPane {
    private Skill skill;
    private Button btn;
    private Text cooldownText;
    private ImageView iconView;
    private boolean isOwnerTurn = false;
    private GameManager gameManager;

    public SkillButton(Skill skill, GameManager gm) {
        this.skill = skill;
        this.gameManager = gm;


        btn = new Button();
        iconView = new ImageView(skill.getIcon());
        iconView.setFitWidth(50);
        iconView.setFitHeight(50);
        btn.setGraphic(iconView);
/*
        if (skill.getIcon() != null) {
            iconView = new ImageView(skill.getIcon());
            iconView.setFitWidth(50);
            iconView.setFitHeight(50);
            btn.setGraphic(iconView);
        } else {
            // ถ้าไม่มีรูป ให้ใส่ชื่อสกิลลงไปแทน
            btn.setText(skill.getName());
            btn.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            btn.setPrefSize(60, 60); // กำหนดขนาดปุ่มให้เท่าๆ กัน
        }

 */



        btn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");

        cooldownText = new Text("");
        cooldownText.setFont(Font.font("Verdana", 24));
        cooldownText.setFill(Color.WHITE);
        cooldownText.setStroke(Color.BLACK);
        cooldownText.setVisible(false);


        btn.setOnAction(e -> {
            if (skill.isReady() && isOwnerTurn) {
                skill.use(gm);
                updateUI();
                gameManager.handleSkillUsage();
            }
        });


        Tooltip.install(btn, new Tooltip(skill.getName()));

        this.getChildren().addAll(btn, cooldownText);
        updateUI();
    }

    public void updateUI() {
        if (skill.isReady() && isOwnerTurn) {
            btn.setDisable(false);
            iconView.setEffect(null);
            //if (iconView != null) iconView.setEffect(null);
            cooldownText.setVisible(false);
        } else {
            btn.setDisable(true);


            ColorAdjust grayscale = new ColorAdjust();
            grayscale.setSaturation(-1);
            iconView.setEffect(grayscale);


            if (!skill.isReady()) {
                cooldownText.setText(String.valueOf(skill.getCurrentCooldown()));
                cooldownText.setVisible(true);
            } else {
                cooldownText.setVisible(false);
            }
        }
    }

    public void setTurnActive(boolean isActive) {
        this.isOwnerTurn = isActive;
        updateUI();
    }

    public void onTurnStart() {
        skill.reduceCooldown();
        setTurnActive(true);
    }
}
