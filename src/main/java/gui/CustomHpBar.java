package gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CustomHpBar extends StackPane {

    private Rectangle bar;
    private Rectangle bg;
    private double maxWidth;

    private Timeline smoothAnim;

    public CustomHpBar(double width, double height) {
        this.maxWidth = width;

        // === Background (เหมือนของเดิม) ===
        bg = new Rectangle(width, height);
        bg.setFill(Color.DARKGRAY);
        bg.setArcWidth(height);
        bg.setArcHeight(height);

        // === HP bar (แบบเดิม) ===
        bar = new Rectangle(width, height);
        bar.setFill(Color.LIMEGREEN);
        bar.setArcWidth(height);
        bar.setArcHeight(height);

        // Default: หดจากซ้ายเข้ากลาง
        StackPane.setAlignment(bar, Pos.CENTER_RIGHT);

        getChildren().addAll(bg, bar);
    }

    public void setHp(double percent) {
        if (percent < 0) percent = 0;
        if (percent > 1) percent = 1;

        double targetWidth = maxWidth * percent;

        // ป้องกันอาการ animation ซ้อนกัน
        if (smoothAnim != null) smoothAnim.stop();

        // Smooth animation
        smoothAnim = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(bar.widthProperty(), bar.getWidth())
                ),
                new KeyFrame(Duration.seconds(0.25),
                        new KeyValue(bar.widthProperty(), targetWidth)
                )
        );

        smoothAnim.play();
    }

    /** ใช้เมื่อเป็นฝั่งขวา (Onion) ให้ HP หดจากขวาเข้ากลาง */
    public void setRightSide() {
        StackPane.setAlignment(bar, Pos.CENTER_LEFT);
    }
}
