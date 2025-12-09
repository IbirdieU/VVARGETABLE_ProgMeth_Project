package entity.base;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {

    protected double width;
    protected double height;
    protected double x;
    protected double y;

    public GameObject(double x,double y) {
        this.x = x;
        this.y = y;
    }

    public abstract Rectangle2D getHitBox();

    public boolean isIntersects(GameObject other) {
        return this.getHitBox().intersects(other.getHitBox());
    }
    public abstract void update(double deltaTime);

    public abstract void render(GraphicsContext gc);


    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
