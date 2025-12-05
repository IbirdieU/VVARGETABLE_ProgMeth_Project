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



public class MainMenu extends VBox {
    private Runnable onHowToAction;
    private Runnable onStartAction;
    private Button howToBtn;
    private Button playBtn;
    public MainMenu() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        Font myFont = Font.loadFont(getClass().getResourceAsStream("/font/comicy.ttf"),50);
        Text welcomeText = new Text("Welcome to VVGETABLE game");
        welcomeText.setFont(myFont);
        welcomeText.setFill(Color.BLACK);
        //welcomeText.setStroke(Color.BLACK);
        //welcomeText.setStrokeWidth(5);
        //welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL,24));

        BackgroundSize backgroundSize = new BackgroundSize(100,100 , true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("background/howTo.png"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,backgroundSize);

        initializeHowToButton();
        initializePlayButton();
        this.setBackground(new Background(backgroundImage));
        this.getChildren().addAll(welcomeText,howToBtn,playBtn);
    }
    private void initializeHowToButton() {
        Button btn = new Button("");
        Text textTitle = new Text("HOW TO PLAY");
        textTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        textTitle.setFill(Color.BLACK);
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(1);

        btn.setGraphic(textTitle);

        btn.setStyle("-fx-background-color: transparent; ");

        btn.setCursor(Cursor.HAND);

        btn.setOnMouseEntered(e -> {
            textTitle.setStroke(Color.WHITE);
            btn.setCursor(Cursor.HAND);
        });

        btn.setOnMouseExited(e -> {
            textTitle.setStroke(Color.BLACK);
            textTitle.setStrokeWidth(1);
        });
        btn.setOnAction(actionEvent -> {
            if (onHowToAction != null) {
                onHowToAction.run();
            }
        });
        this.howToBtn = btn;
    }
    private void initializePlayButton() {
        Button btn = new Button("");
        Text textTitle = new Text("PLAY");
        textTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(1);

        btn.setGraphic(textTitle);

        btn.setStyle("-fx-background-color: transparent; ");

        btn.setCursor(Cursor.HAND);

        btn.setOnMouseEntered(e -> {
            textTitle.setStroke(Color.WHITE);
            btn.setCursor(Cursor.HAND);
        });

        btn.setOnMouseExited(e -> {
            textTitle.setStroke(Color.BLACK);
            textTitle.setStrokeWidth(1);
        });
        btn.setOnAction(actionEvent -> {
            if (onStartAction != null) {
                onStartAction.run();
            }
        });
        this.playBtn = btn;
    }
    public void setOnHowToAction(Runnable onHowToAction) {
        this.onHowToAction = onHowToAction;
    }

    public void setOnStartAction(Runnable onStartAction) {
        this.onStartAction = onStartAction;
    }
}
