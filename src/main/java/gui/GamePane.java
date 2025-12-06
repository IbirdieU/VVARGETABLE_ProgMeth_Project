package gui;

import entity.base.GameObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class GamePane extends StackPane {
    private final Canvas canvas;
    private final ArrayList<GameObject> allObjects;
    private final GraphicsContext gc;

    public GamePane(ArrayList<GameObject> allObjects) {
        super();
        this.canvas = new Canvas(1280, 720);
        this.allObjects = allObjects;
        this.gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("background/inGameBackgound.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        this.setBackground(new Background(backgroundImage));
    }

    public void drawObjects() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (GameObject object : allObjects) {
            object.render(gc);
        }
    }

}
