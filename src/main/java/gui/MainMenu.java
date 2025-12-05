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

        /// How to button
        Button helpBtn = new Button("");
        Text textTitle = new Text("HOW TO PLAY");
        textTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 30));
        textTitle.setFill(Color.BLACK);
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(1);

        helpBtn.setGraphic(textTitle);

        helpBtn.setStyle("-fx-background-color: transparent; ");

        helpBtn.setCursor(Cursor.HAND);

        helpBtn.setOnMouseEntered(e -> {
            textTitle.setStroke(Color.WHITE);
            helpBtn.setCursor(Cursor.HAND);
        });

        helpBtn.setOnMouseExited(e -> {
            textTitle.setStroke(Color.BLACK);
            textTitle.setStrokeWidth(1);
        });

        /// Old version
        //Button helpBtn = new Button("How to Play"); //how to play button
        //helpBtn.setFont(Font.font("Tahoma", FontWeight.BOLD,24));
        //helpBtn.setStyle("-fx-background-radius: 20;");
        //helpBtn.setPrefWidth(220);
        //helpBtn.setBackground(new Background(new BackgroundFill(Color.web("#4D869C"), null, null)));
        //helpBtn.setOnMouseEntered(e -> helpBtn.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.web("#7AB2B2"), null, null))));
        //helpBtn.setOnMouseExited(e -> helpBtn.setBackground(new Background(new BackgroundFill(Color.web("#4D869C"), null, null))));


        helpBtn.setOnAction(actionEvent -> {
            if (onHowToAction != null) {
                onHowToAction.run();
            }
        });

        /// Play button
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


        /// Old version
        //Button plyBtn = new Button("Play");//play button
        //plyBtn.setFont(Font.font("Tahoma", FontWeight.BOLD,24));
        //plyBtn.setStyle("-fx-background-radius: 20;");
        //plyBtn.setPrefWidth(220);
        //plyBtn.setBackground(new Background(new BackgroundFill(Color.web("#4D869C"), new CornerRadii(20), null)));
        //plyBtn.setOnMouseEntered(e -> plyBtn.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.web("#7AB2B2"), new CornerRadii(20), null))));
        //plyBtn.setOnMouseExited(e -> plyBtn.setBackground(new Background(new BackgroundFill(Color.web("#4D869C"), new CornerRadii(20), null))));
        //Btn action will implement later cus now dont game pane;


        plyBtn.setOnAction(actionEvent -> {

        });
        this.setBackground(new Background(backgroundImage));
        this.getChildren().addAll(welcomeText,helpBtn,plyBtn);
    }

    public void setOnHowToAction(Runnable onHowToAction) {
        this.onHowToAction = onHowToAction;
    }
}
