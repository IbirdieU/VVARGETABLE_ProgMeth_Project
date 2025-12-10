package entity.unit;

import entity.base.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.mechanics.Wind;

public class Projectile extends GameObject {

    private static final double GRAVITY = 1750; // pixels/second^2

    private final Image image;
    private double vx;
    private double vy;
    private final double initialVx;
    private boolean isDestroyed = false;

    private double angleRotation = 0;
    private final double spinSpeed = 360;
    private static final double DEFAULT_HITBOX = 48.0;
    private final ProjectileStats stats;
    private final Wind wind;

    public Projectile(double startX, double startY, Image image, double angle, double power, Wind wind, ProjectileStats stats) {
        super(startX, startY);

        this.image = image;
        this.stats = stats;
        this.wind = wind;

        setWidth(image.getWidth() * 0.15 * stats.getScale());
        setHeight(image.getHeight() * 0.15 * stats.getScale());

        setX(startX);
        setY(startY);

        double velocityPower = power * 18.0; // Scale down for velocity calculation
        this.vx = velocityPower * Math.cos(Math.toRadians(angle));
        this.vy = velocityPower * Math.sin(Math.toRadians(angle));
        this.initialVx = this.vx;
    }

    @Override
    public Rectangle2D getHitBox() {
        if (stats.getScale() > 1) {
            return new Rectangle2D(getX(), getY(), DEFAULT_HITBOX*2, DEFAULT_HITBOX*2);
        }
        return new Rectangle2D(getX(), getY(), DEFAULT_HITBOX, DEFAULT_HITBOX);
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
        if (initialVx != 0) {
            return stats.getDamage() * (Math.abs(vx) / Math.abs(initialVx));
        }
        return stats.getDamage();
    }

    public int getPoisonDuration() {
        return stats.getPoisonDuration();
    }

    public boolean isStun() {
        return stats.isStun();
    }

    public boolean isPoisonous() {
        return stats.isPoisonous();
    }

    public double getPoisonDamage() {
        return stats.getPoisonDamage();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}
