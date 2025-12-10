package util;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UIButton {

    public static StackPane SignButton(String content, String imagePath, Runnable action){
        Image img = null;
        try {
            img = new Image(imagePath);
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