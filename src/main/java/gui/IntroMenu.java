package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
        BackgroundImage backgroundImage = new BackgroundImage(new Image("background/intro.png"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,backgroundSize);

        HBox characterRow = new HBox(50);
        characterRow.setAlignment(Pos.BASELINE_CENTER);
        characterRow.getChildren().addAll(new ImageView(carrotImg), new ImageView(onionImg));

        Font myFont = Font.loadFont(getClass().getResourceAsStream("/font/start.ttf"),60);
        Text name = new Text("VVARGETABLE");
        name.setFont(myFont);

        Button startBtn = new Button("START");
        startBtn.setFont(Font.font("Tahoma", FontWeight.BOLD,24));
        startBtn.setStyle("-fx-background-radius: 20;");
        startBtn.setPrefWidth(160);
        startBtn.setBackground(new Background(new BackgroundFill(Color.web("#4D869C"), new CornerRadii(20), null)));
        startBtn.setOnMouseEntered(e -> startBtn.setBackground(new Background(new BackgroundFill(Color.web("#7AB2B2"), new CornerRadii(20), null))));
        startBtn.setOnMouseExited(e -> startBtn.setBackground(new Background(new BackgroundFill(Color.web("#4D869C"), new CornerRadii(20), null))));
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
