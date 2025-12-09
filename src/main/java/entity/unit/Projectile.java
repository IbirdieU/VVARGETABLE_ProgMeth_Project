package entity.unit;

import entity.base.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.Wind;

public class Projectile extends GameObject {

    private static final double GRAVITY = 1750; // pixels/second^2

    private Image image;
    private double vx;
    private double vy;
    private boolean isDestroyed = false;

    private double angleRotation = 0;
    private double spinSpeed = 360;

    private double damage;
    private boolean isPoisonous;
    private int poisonDuration;
    private double poisonDamage;
    private Wind wind;
    private boolean isStun;

    public Projectile(double startX, double startY, Image image, double angle, double power, double damage,
                       double scale, boolean isPoison, Wind wind,boolean isStun) {

        super(startX, startY);


        this.image = image;
        setWidth(image.getWidth()*0.15*scale);
        setHeight(image.getHeight()*0.15*scale);


        setX(startX);
        setY(startY);


        double velocityPower = power*18.0; // Scale down for velocity calculation
        this.vx = velocityPower * Math.cos(Math.toRadians(angle));
        this.vy = velocityPower * Math.sin(Math.toRadians(angle));


        this.damage = damage;
        this.isPoisonous = isPoison;
        this.wind = wind;
        this.isStun = isStun;

        if (isPoison) {
            this.poisonDuration = 2;
            this.poisonDamage = 10;
        } else {
            this.poisonDuration = 0;
            this.poisonDamage = 0;
        }
    }

    @Override
    public Rectangle2D getHitBox() {
        if (image.getUrl().contains("carrot")) {
            return new Rectangle2D(getX()+8,getY(),40,40);
        }
        return new Rectangle2D(getX(),getY(),40,40);
    }

    @Override
    public boolean isIntersects(GameObject other) {
        return false;
    }

    @Override
    public void update(double deltaTime) {
        // Apply gravity
        angleRotation += spinSpeed * deltaTime;
        vy += GRAVITY * deltaTime;

        // Apply wind
        vx += wind.getStrength() * deltaTime * 3.0;


        // Update position
        setX(x + vx * deltaTime);
        setY(y + vy * deltaTime);

        // Simple boundary check to destroy projectile
        if (y > 720 || x < 0 || x > 1280) {
            this.isDestroyed = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (image != null) {

            gc.save();


            gc.translate(getX() + getWidth() / 2, getY() + getHeight() / 2);


            gc.rotate(angleRotation);


            gc.drawImage(image, -getWidth() / 2, -getHeight() / 2, getWidth(), getHeight());

            gc.restore();
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

    public boolean isStun() { return isStun; }

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
