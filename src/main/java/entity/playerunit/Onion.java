package entity.playerunit;

import entity.base.Character;
import entity.base.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class Onion extends Character {

    public Onion(double x, double y, int health) {
        super(x, y, health);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public boolean intersects(GameObject other) {
        return false;
    }
}
