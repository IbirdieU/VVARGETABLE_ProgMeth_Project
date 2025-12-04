package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainMenu extends VBox {
    public MainMenu() {


        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);

        Image carrotImg = new Image("playerImage/carrot.png");
        Image onionImg = new Image("playerImage/onion.png");

        HBox characterRow = new HBox(50);
        characterRow.setAlignment(Pos.CENTER);
        characterRow.getChildren().addAll(new ImageView(carrotImg), new ImageView(onionImg));

        Button startBtn = new Button("START");
        Font myFont = Font.loadFont(getClass().getResourceAsStream("/font/start.ttf"),20);

        startBtn.setFont(myFont);
        //startBtn action will implement later cus now dont have how to play pane;
        startBtn.setOnAction(actionEvent -> {

        });


        this.getChildren().addAll(characterRow, startBtn);
    }



}
