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
import util.UIButton;


public class MainMenu extends VBox {
    private Runnable onHowToAction;
    private Runnable onStartAction;
    public MainMenu() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        BackgroundSize backgroundSize = new BackgroundSize(100,100 , true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("background/howTo.png"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,backgroundSize);
        this.setBackground(new Background(backgroundImage));


        Font myFont = Font.loadFont(getClass().getResourceAsStream("/font/comicy.ttf"),50);

        Text welcomeText = new Text("Welcome to VVGETABLE game");
        welcomeText.setFont(myFont);

        /// How to button
        StackPane helpBtn = UIButton.SignButton("HOW TO PLAY","/background/woodSign.png",() -> {
            if (onHowToAction != null) {
                onHowToAction.run();
            }
        });

        /// Play button
        StackPane plyBtn = UIButton.SignButton("PLAY","/background/woodSign.png",() -> {
            if (onStartAction != null) {
                onStartAction.run();
            }
        });

        this.getChildren().addAll(welcomeText,helpBtn,plyBtn);
    }

    public void setOnHowToAction(Runnable onHowToAction) {
        this.onHowToAction = onHowToAction;
    }

    public void setOnStartAction(Runnable onStartAction) {
        this.onStartAction = onStartAction;
    }

}
