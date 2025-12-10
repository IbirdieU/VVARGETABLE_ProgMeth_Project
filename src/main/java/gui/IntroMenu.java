package gui;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.managers.SoundManager;

public class IntroMenu extends VBox {
    private Runnable onAction;
    public IntroMenu() {


        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);

        BackgroundSize backgroundSize = new BackgroundSize(100,100 , true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResourceAsStream("/background/opening.png")),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,backgroundSize);

        Font myFont = Font.loadFont(getClass().getResourceAsStream("/font/bringa.ttf"),140);
        Text header = new Text("VVARGETABLE");
        header.setFont(myFont);
        header.setFill(Color.WHITE);
        header.setStroke(Color.BLACK);
        header.setStrokeWidth(5);

        //Add shadow
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.color(0, 0, 0, 0.5));
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        header.setEffect(dropShadow);


        Button startBtn = new Button("");
        Text textTitle = new Text("START GAME");
        textTitle.setFont(Font.font("comicy", FontWeight.BOLD, 30));
        textTitle.setFill(Color.BLACK);
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(1);

        //set text to btn
        startBtn.setGraphic(textTitle);

        //Transparent Background
        startBtn.setStyle("-fx-background-color: transparent; ");

        //Change cursor when enter
        startBtn.setCursor(Cursor.HAND);

        startBtn.setOnMouseEntered(e -> {
            textTitle.setStroke(Color.WHITE);
            SoundManager.playHoverSound();
        });

        startBtn.setOnMouseExited(e -> {
            textTitle.setStroke(Color.BLACK);
            textTitle.setStrokeWidth(1);
        });

        startBtn.setOnAction(actionEvent -> {
            SoundManager.playClickSound();
            if (onAction != null) {
                onAction.run();
            }
        });

        this.setBackground(new Background(backgroundImage));
        this.getChildren().addAll(header,startBtn);

    }

    public void setOnAction(Runnable onAction) {
        this.onAction = onAction;
    }
}
