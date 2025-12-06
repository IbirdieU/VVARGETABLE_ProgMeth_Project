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

public class HowToMenu extends VBox {
    private Runnable onStartAction;

    public HowToMenu() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        BackgroundSize backgroundSize = new BackgroundSize(100,100 , true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("background/howTo.png"), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,backgroundSize);

        Text head = new Text("HOW TO PLAY");
        Font myFont = Font.loadFont(getClass().getResourceAsStream("/font/comicy.ttf"),50);
        head.setFont(myFont);

        Text line1 = new Text("Players can click and hold mouse button over their turn");
        line1.setFont(Font.font("Tahoma", FontWeight.NORMAL,24));

        Text line2 = new Text("power gauge increases until released");
        line2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line2.setFill(Color.DARKGRAY);

        Text line3 = new Text("Release mouse button to throw items");
        line3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line3.setFill(Color.DARKGRAY);

        Text line4 = new Text("When time ran out, the turn will be skipped");
        line4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line4.setFill(Color.RED);

        Text line5 = new Text("Click to select the special skills before you throw. Each cooldown is 4 turn");
        line5.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line5.setFill(Color.BLACK);

        /// Skill Descriptions
        HBox skillRow = new HBox(30);
        skillRow.setAlignment(Pos.CENTER);

        VBox item1 = SkillDescription("/icon/healCarrot.png", "HEALING CARROT");
        VBox item2 = SkillDescription("/icon/doubleCarrot.png", "DOUBLE CARROT");
        VBox item3 = SkillDescription("/icon/toxicOnion.png", "TOXIC ONION");
        VBox item4 = SkillDescription("/icon/growthOnion.png", "GROWTH ONION");

        skillRow.getChildren().addAll(item1, item2, item3, item4);


        /// Play button
        StackPane plyBtn = SignButton("PLAY","/background/woodSign.png",() -> {
            if (onStartAction != null) {
                onStartAction.run();
            }
        });

        this.setBackground(new Background(backgroundImage));
        this.getChildren().addAll(head,line1,line2,line3,line4,line5,skillRow,plyBtn);
    }

    public void setOnStartAction(Runnable onStartAction) {
        this.onStartAction = onStartAction;
    }

    //Skill description
    private VBox SkillDescription(String imagePath, String description){
        Image img = null;
        try {
            img = new Image(getClass().getResourceAsStream(imagePath));
        } catch (NullPointerException e) {
            System.out.println("Not Found: " + imagePath);
        }
        ImageView skillIcon = new ImageView(img);
        skillIcon.setFitWidth(70);
        skillIcon.setPreserveRatio(true);

        Text descText = new Text(description);
        descText.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        descText.setFill(Color.web("#3E2723"));

        VBox itemBox = new VBox(10);
        itemBox.setAlignment(Pos.CENTER);
        itemBox.getChildren().addAll(skillIcon, descText);

        return itemBox;
    }

    //Button
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
