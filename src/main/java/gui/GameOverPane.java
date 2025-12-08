package gui;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameOverPane extends StackPane {
    private Runnable onRestart;
    private Runnable onExit;

    public GameOverPane(String winnerName) {
        this.setAlignment(Pos.CENTER);


        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");


        StackPane window = new StackPane();
        window.setMaxSize(400, 300);


        Image bgImg = new Image(ClassLoader.getSystemResourceAsStream("background/howTo.png"));
        ImageView bgView = new ImageView(bgImg);
        bgView.setFitWidth(860);
        bgView.setFitHeight(480);


        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        Text title = new Text(winnerName + " WINS!");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
        title.setFill(Color.BLACK);

        StackPane replyBtn = SignButton("RETRY","/background/woodSign.png",() -> {
            if (onRestart != null) {
                onRestart.run();
            }
        });

        StackPane exitBtn = SignButton("QUIT","/background/woodSign.png",() -> {
            if (onExit != null) {
                onExit.run();
            }
        });

        HBox btnRow = new HBox(30);
        btnRow.setAlignment(Pos.CENTER);
        btnRow.getChildren().addAll(replyBtn,exitBtn);

        content.getChildren().addAll(title,btnRow);

        window.getChildren().addAll(bgView, content);

        this.getChildren().add(window);
    }

    public void setOnRestart(Runnable onRestart) {this.onRestart = onRestart;}

    public void setOnExit(Runnable onExit) {this.onExit = onExit;}

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
