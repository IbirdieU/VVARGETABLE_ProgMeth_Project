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
import logic.managers.SoundManager;
import util.UIButton;

public class HowToMenu extends VBox {
    private Runnable onStartAction;

    public HowToMenu() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        BackgroundSize backgroundSize = new BackgroundSize(100,100 , true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("background/howTo.png"), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,backgroundSize);

        Text header = new Text("HOW TO PLAY");
        Font myFont = Font.loadFont(getClass().getResourceAsStream("/font/comicy.ttf"),50);
        header.setFont(myFont);

        Text line1 = new Text("Players can click and hold mouse button over their turn");
        line1.setFont(Font.font("Tahoma", FontWeight.NORMAL,24));

        Text line2 = new Text("Power gauge increases until released");
        line2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line2.setFill(Color.DARKCYAN);

        Text line3 = new Text("Watch wind meter to measure throw");
        line3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line3.setFill(Color.DARKCYAN);

        Text line4 = new Text("Release mouse button to throw items");
        line4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line4.setFill(Color.DARKCYAN);

        Text line5 = new Text("When time ran out, the turn will be skipped");
        line5.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line5.setFill(Color.RED);

        Text line6 = new Text("Click to select the special skills before you throw. Each cooldown is 4 turn");
        line6.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        line6.setFill(Color.BLACK);

        /// Skill Descriptions
        HBox skillRow = new HBox(30);
        skillRow.setAlignment(Pos.CENTER);

        VBox item1 = SkillDescription("/icon/skillIcon/healCarrot.png", "HEALING CARROT");
        VBox item2 = SkillDescription("/icon/skillIcon/doubleCarrot.png", "DOUBLE CARROT");
        VBox item3 = SkillDescription("/icon/skillIcon/stunnedCarrot.png", "TOXIC ONION");
        VBox item4 = SkillDescription("/icon/skillIcon/toxicOnion.png", "GROWTH ONION");
        VBox item5 = SkillDescription("/icon/skillIcon/growthOnion.png", "GROWTH ONION");
        VBox item6 = SkillDescription("/icon/skillIcon/shieldedOnion.png","SHIELDED ONION");
        skillRow.getChildren().addAll(item1, item2, item3, item4, item5, item6);


        /// Play button
        StackPane plyBtn = UIButton.SignButton("PLAY","/background/woodSign.png",() -> {
            if (onStartAction != null) {
                onStartAction.run();
            }
        });

        this.setBackground(new Background(backgroundImage));
        this.getChildren().addAll(header,line1,line2,line3,line4,line5,line6,skillRow,plyBtn);
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

}
