package entity.unit;

import entity.base.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class Wall extends GameObject {
    public Wall(double x, double y) {
        super(x, y);

    }

    @Override
    public Rectangle2D getHitBox() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }


}
