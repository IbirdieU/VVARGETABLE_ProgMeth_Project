package entity.playerunit;

import entity.base.Character;
import entity.base.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Carrot extends Character  {

    private static final Image IMG = new Image("playerImage/carrot.png");

    public Carrot(double x, double y, int health) {
        super(x, y, health);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public void onCollision(GameObject other) {

    }
}
