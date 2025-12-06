package entity.playerunit;

import entity.base.Character;
import entity.base.GameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Carrot extends Character  {

    private static final Image IMG = new Image("playerImage/carrot.png");

    public Carrot(double x, double y, int health) {
        super(x, y, health);
        this.width = IMG.getWidth();
        this.height = IMG.getHeight();
    }

    @Override
    public void update() {
        // Update logic for the carrot would go here
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(IMG, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void onCollision(GameObject other) {
        // Collision logic would go here
    }
}
