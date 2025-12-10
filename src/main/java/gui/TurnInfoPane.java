package gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.enums.PlayerTurn;
import logic.mechanics.Wind;

public class TurnInfoPane extends VBox {
    private Text turnText;
    private Text timerText;
    private Rectangle windBar;
    private ImageView windArrow;
    private StackPane windBarContainer;
    private final double MAX_WIND_BAR_WIDTH = 60;
    private final double MAX_WIND_STRENGTH = 100; // Corresponds to MAX_STRENGTH in Wind class
    private int lastWindDirection = 0;

    public TurnInfoPane() {
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

        //Wind frame
        Rectangle windFrame = new Rectangle(MAX_WIND_BAR_WIDTH*2, 10);
        windFrame.setFill(Color.CHOCOLATE);
        windFrame.setStroke(Color.BLACK);
        windFrame.setStrokeWidth(1);
        // Wind bar
        windBar = new Rectangle(0, 10);
        windBar.setFill(Color.LIGHTBLUE);

        windBarContainer = new StackPane(windFrame,windBar);
        windBarContainer.setAlignment(Pos.CENTER);
        windBarContainer.setPrefWidth(MAX_WIND_BAR_WIDTH);


        // Wind arrow
        Image arrowImage = new Image(getClass().getResourceAsStream("/icon/otherIcon/arrow.png"));
        windArrow = new ImageView(arrowImage);
        windArrow.setFitWidth(100);
        windArrow.setFitHeight(80);
        windArrow.setPreserveRatio(true);

        this.getChildren().addAll(turnText, timerText, windBarContainer, windArrow);
    }


    public void updateUI(int seconds, PlayerTurn currentTurn, Wind wind) {
        // Update timer
        timerText.setText(String.valueOf(seconds));
        if (seconds <= 5) {
            timerText.setFill(Color.RED);
        } else {
            timerText.setFill(Color.WHITE);
        }

        // Update turn text
        if (currentTurn == PlayerTurn.PLAYER_ONE) {
            turnText.setText("Carrot Turn");
            turnText.setFill(Color.ORANGE);
        } else {
            turnText.setText("Onion Turn");
            turnText.setFill(Color.PURPLE);
        }

        // Update wind indicator
        double windStrength = wind.getStrength();
        double windMagnitude = wind.getStrengthMagnitude();
        int windDirection = wind.getDirection();

        // Normalize the wind magnitude to a 0-1 range
        double normalizedStrength = windMagnitude / MAX_WIND_STRENGTH;
        double barWidth = normalizedStrength * MAX_WIND_BAR_WIDTH;

        windBar.setWidth(barWidth);
        // Set the position of the bar based on wind direction
        if (windStrength > 0) { // Wind to the right
            windBar.setTranslateX(barWidth / 2);
        } else if (windStrength < 0) { // Wind to the left
            windBar.setTranslateX(-barWidth / 2);
        } else { // No wind
            windBar.setTranslateX(0);
        }



        if (windDirection != 0) {
            if (lastWindDirection != 0 && windDirection == -lastWindDirection) {
                // Opposite direction, flip the image
                windArrow.setScaleX(windArrow.getScaleX() * -1);
            } else if (lastWindDirection == 0) {
                // Wind was 0, now it's not. Set the direction.
                if (windDirection > 0) {
                    windArrow.setScaleX(1);
                } else { // windDirection < 0
                    windArrow.setScaleX(-1);
                }
            }
            windArrow.setVisible(true);
        } else {
            windArrow.setVisible(false);
        }
        lastWindDirection = windDirection;
    }
}
