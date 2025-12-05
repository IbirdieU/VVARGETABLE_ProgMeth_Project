package gui;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class HowToMenu extends VBox {
    private Runnable onStartAction;

    public HowToMenu() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        Text line1 = new Text("Players can click and hold mouse button over their turn");
        //howToText.setStroke(Color.BLACK);
        //howToText.setStrokeWidth(5);
        line1.setFont(Font.font("Tahoma", FontWeight.NORMAL,24));

        Text line2 = new Text("power gauge increases until released");
        line2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line2.setFill(Color.GRAY);

        Text line3 = new Text("Release mouse button to throw items");
        line3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line3.setFill(Color.GRAY);

        Text line4 = new Text("When time ran out, the turn will be skipped");
        line4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line4.setFill(Color.RED);

        BackgroundSize backgroundSize = new BackgroundSize(100,100 , true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("background/howTo.png"), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,backgroundSize);


        Button plyBtn = new Button("");
        Text textTitle2 = new Text("PLAY");
        textTitle2.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        textTitle2.setStroke(Color.BLACK);
        textTitle2.setStrokeWidth(1);

        plyBtn.setGraphic(textTitle2);

        plyBtn.setStyle("-fx-background-color: transparent; ");

        plyBtn.setCursor(Cursor.HAND);

        plyBtn.setOnMouseEntered(e -> {
            textTitle2.setStroke(Color.WHITE);
            plyBtn.setCursor(Cursor.HAND);
        });

        plyBtn.setOnMouseExited(e -> {
            textTitle2.setStroke(Color.BLACK);
            textTitle2.setStrokeWidth(1);
        });

        plyBtn.setOnAction(actionEvent -> {
            if (onStartAction != null) {
                onStartAction.run();
            }
        });
        this.setBackground(new Background(backgroundImage));
        this.getChildren().addAll(line1,line2,line3,line4,plyBtn);
    }

    public void setOnStartAction(Runnable onStartAction) {
        this.onStartAction = onStartAction;
    }
}
