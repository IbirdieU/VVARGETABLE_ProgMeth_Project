package gui;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.PlayerTurn;

public class TurnTimerPane extends VBox {
    private Text turnText;
    private Text timerText;

    public TurnTimerPane() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(5);
        this.setTranslateY(20);
        this.setMouseTransparent(true);

        turnText = new Text("");
        turnText.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        turnText.setStroke(Color.BLACK);
        turnText.setStrokeWidth(1);

        timerText = new Text("");
        timerText.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        timerText.setStroke(Color.BLACK);
        timerText.setStrokeWidth(2);

        this.getChildren().addAll(turnText, timerText);
    }


    public void updateUI(int seconds, PlayerTurn currentTurn) {
        timerText.setText(String.valueOf(seconds));

        if (seconds <= 5) {
            timerText.setFill(Color.RED);
        } else {
            timerText.setFill(Color.WHITE);
        }

        if (currentTurn == PlayerTurn.PLAYER_ONE) {
            turnText.setText("Carrot Turn");
            turnText.setFill(Color.ORANGE);
        } else {
            turnText.setText("Onion Turn");
            turnText.setFill(Color.PURPLE);
        }
    }
}