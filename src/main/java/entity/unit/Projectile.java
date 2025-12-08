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

    private double damage;
    private boolean isPoisonous;
    private int poisonDuration;
    private double poisonDamage;

    public Projectile(double startX, double startY, Image image, double angle, double power, double damage,
                      double scale, boolean isPoison) {

        super(startX, startY);


        this.image = image;
        setWidth(image.getWidth()*0.2*scale);
        setHeight(image.getHeight()*0.2*scale);


        setX(startX);
        setY(startY);


        this.vx = power * Math.cos(Math.toRadians(angle));
        this.vy = power * Math.sin(Math.toRadians(angle));


        this.damage = damage;
        this.isPoisonous = isPoison;

        if (isPoison) {
            this.poisonDuration = 2;
            this.poisonDamage = damage*0.5;
        } else {
            this.poisonDuration = 0;
            this.poisonDamage = 0;
        }
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D(getX(),getY(),50,50);
    }

    @Override
    public boolean isIntersects(GameObject other) {
        return false;
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

    public double getDamage() {
        return damage;
    }

    public int getPoisonDuration() {
        return poisonDuration;
    }

    public boolean isPoisonous() {
        return isPoisonous;
    }

    public double getPoisonDamage() {
        return poisonDamage;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}