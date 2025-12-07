package entity.unit;

import entity.base.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Projectile extends GameObject {

    private static final double GRAVITY = 0.5;

    private Image image;
    private double vx;
    private double vy;
    private boolean isDestroyed = false;

    public Projectile(double startX, double startY, Image image, double angle, double power) {

        super(startX, startY);


        this.image = image;
        setWidth(image.getWidth()/5);
        setHeight(image.getHeight()/5);


        setX(startX);
        setY(startY);


        this.vx = power * Math.cos(Math.toRadians(angle));
        this.vy = power * Math.sin(Math.toRadians(angle));
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D(getX(),getY(),50,50);
    }

    @Override
    public void update() {
        // Apply gravity
        vy += GRAVITY;

        // Update position
        setX(x + vx);
        setY(y + vy);

        // Simple boundary check to destroy projectile
        if (y > 720 || x < 0 || x > 1280) {
            this.isDestroyed = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (image != null) {
            gc.drawImage(image, getX(), getY(), getWidth(), getHeight());
        }
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}
