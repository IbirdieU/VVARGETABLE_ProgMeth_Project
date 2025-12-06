package gui;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;



public class MainMenu extends VBox {
    private Runnable onHowToAction;
    private Runnable onStartAction;
    public MainMenu() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        BackgroundSize backgroundSize = new BackgroundSize(100,100 , true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("background/howTo.png"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,backgroundSize);

        Font myFont = Font.loadFont(getClass().getResourceAsStream("/font/comicy.ttf"),50);

        Text welcomeText = new Text("Welcome to VVGETABLE game");
        welcomeText.setFont(myFont);

        /// How to button
        StackPane helpBtn = SignButton("HOW TO PLAY","/background/woodSign.png",() -> {
            if (onHowToAction != null) {
                onHowToAction.run();
            }
        });

        /// Play button
        StackPane plyBtn = SignButton("PLAY","/background/woodSign.png",() -> {
            if (onStartAction != null) {
                onStartAction.run();
            }
        });

        this.setBackground(new Background(backgroundImage));
        this.getChildren().addAll(welcomeText,helpBtn,plyBtn);
    }

    public void setOnHowToAction(Runnable onHowToAction) {
        this.onHowToAction = onHowToAction;
    }

    public void setOnStartAction(Runnable onStartAction) {
        this.onStartAction = onStartAction;
    }



    private StackPane SignButton(String content,String imagePath,Runnable action){
        Image img = null;
        try {
            img = new Image(getClass().getResourceAsStream(imagePath));
        } catch (NullPointerException e) {
            System.out.println("Not Found: " + imagePath);
        }
        ImageView sign = new ImageView(img);

        sign.setFitWidth(300);
        sign.setPreserveRatio(true);
        sign.setTranslateY(-7);
        sign.setTranslateX(4);

        Button btn = new Button("");
        Text textTitle = new Text(content);
        textTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(1);

        btn.setGraphic(textTitle);

        btn.setStyle("-fx-background-color: transparent; ");

        btn.setCursor(Cursor.HAND);

        btn.setOnMouseEntered(e -> {
            textTitle.setStroke(Color.WHITE);
        });

        btn.setOnMouseExited(e -> {
            textTitle.setStroke(Color.BLACK);
        });

        btn.setOnAction(e -> {
            if (action != null) action.run();
        });

        StackPane pane = new StackPane();
        pane.getChildren().addAll(sign,btn);

        return pane;
    }
}
