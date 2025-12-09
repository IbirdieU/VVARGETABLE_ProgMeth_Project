package gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.UIButton;

public class ModeSelectMenu extends VBox {
    private Runnable onDoomedAction;
    private Runnable onClassicAction;

    public ModeSelectMenu(){
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);

        BackgroundSize backgroundSize = new BackgroundSize(100,100 , true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("background/howTo.png"), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,backgroundSize);
        this.setBackground(new Background(backgroundImage));

        Font myFont = Font.loadFont(getClass().getResourceAsStream("/font/comicy.ttf"),50);

        Text welcomeText = new Text("SELECT MODE");
        welcomeText.setFont(myFont);

        /// DOOMED MODE
        StackPane dmBtn = UIButton.SignButton("DOOMED","/background/woodSign.png",() -> {
            if (onDoomedAction != null) {
                onDoomedAction.run();
            }
        });

        ImageView dmIconL = new ImageView(new Image(getClass().getResourceAsStream("/icon/otherIcon/doomedModeIconL.png"))); // แก้ path รูป
        dmIconL.setFitWidth(120);
        dmIconL.setFitHeight(78);
        dmIconL.setTranslateX(-100);
        dmIconL.setMouseTransparent(true);

        ImageView dmIconR = new ImageView(new Image(getClass().getResourceAsStream("/icon/otherIcon/doomedModeIconR.png"))); // แก้ path รูป
        dmIconR.setFitWidth(80);
        dmIconR.setFitHeight(52);
        dmIconR.setTranslateX(100);
        dmIconR.setTranslateY(12);
        dmIconR.setMouseTransparent(true);

        dmBtn.getChildren().addAll(dmIconL, dmIconR);


        /// CLASSIC MODE
        StackPane clsBtn = UIButton.SignButton("CLASSIC","/background/woodSign.png",() -> {
            if (onClassicAction != null) {
                onClassicAction.run();
            }
        });

        ImageView clsIconL = new ImageView(new Image(getClass().getResourceAsStream("/icon/otherIcon/classicModeIconL.png")));
        clsIconL.setFitWidth(70);
        clsIconL.setFitHeight(75);
        clsIconL.setTranslateX(-90);
        clsIconL.setTranslateY(-5);
        clsIconL.setMouseTransparent(true);

        ImageView clsIconR = new ImageView(new Image(getClass().getResourceAsStream("/icon/otherIcon/classicModeIconR.png")));
        clsIconR.setFitWidth(50);
        clsIconR.setFitHeight(72);
        clsIconR.setTranslateX(90);
        clsIconR.setTranslateY(-5);
        clsIconR.setMouseTransparent(true);

        clsBtn.getChildren().addAll(clsIconL, clsIconR);

        this.getChildren().addAll(welcomeText, dmBtn, clsBtn);
    }

    public void setOnDoomedAction(Runnable onDoomedAction) {this.onDoomedAction = onDoomedAction;
    }

    public void setOnClassicAction(Runnable onClassicAction) {
        this.onClassicAction = onClassicAction;
    }
}
