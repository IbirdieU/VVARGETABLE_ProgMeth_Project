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

public class IntroMenu extends VBox {
    private Runnable onAction;
    public IntroMenu() {


        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);

        Image carrotImg = new Image("playerImage/carrot.png");
        Image onionImg = new Image("playerImage/onion.png");

        BackgroundSize backgroundSize = new BackgroundSize(100,100 , true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("background/opening.png"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,backgroundSize);


        Font myFont = Font.loadFont(getClass().getResourceAsStream("/font/bringa.ttf"),140);
        Text name = new Text("VVARGETABLE");
        name.setFont(myFont);
        name.setFill(Color.WHITE);
        name.setStroke(Color.BLACK);
        name.setStrokeWidth(5);

        //Add shadow
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.color(0, 0, 0, 0.5));
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        name.setEffect(dropShadow);


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
            startBtn.setCursor(Cursor.HAND);
        });

        startBtn.setOnMouseExited(e -> {
            textTitle.setStroke(Color.BLACK);
            textTitle.setStrokeWidth(1);
        });

        /// Old version

        //Button startBtn = new Button("START");
        //startBtn.setFont(Font.font("comicy", FontWeight.BOLD,24));
        //startBtn.setStyle("-fx-background-radius: 20;");
        //startBtn.setPrefWidth(160);
        //startBtn.setBackground(new Background(new BackgroundFill(Color.web("#4D869C"), new CornerRadii(20), null)));
        //startBtn.setOnMouseEntered(e -> startBtn.setBackground(new Background(new BackgroundFill(Color.web("#7AB2B2"), new CornerRadii(20), null))));
        //startBtn.setOnMouseExited(e -> startBtn.setBackground(new Background(new BackgroundFill(Color.web("#4D869C"), new CornerRadii(20), null))));

        startBtn.setOnAction(actionEvent -> {
            if (onAction != null) {
                onAction.run();
            }
        });

        this.setBackground(new Background(backgroundImage));
        this.getChildren().addAll(name,startBtn);

    }

    public void setOnAction(Runnable onAction) {
        this.onAction = onAction;
    }
}
