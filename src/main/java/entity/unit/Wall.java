package entity.unit;

import entity.base.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall extends GameObject {
    public Wall(double x, double y, double width, double height) {
        super(x, y);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D(getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.rgb(255, 25, 0, 0.6)); //
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
    }


}
