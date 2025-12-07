package entity.playerunit;

import entity.base.Character;
import entity.base.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Carrot extends Character {

    private static final Image NORMAL_IMG = new Image("playerImage/carrot.png");
    private static final Image ATTACK_IMG = new Image("playerImage/attackCarrot.png");
    private static final Image DAMAGED_IMG = new Image("playerImage/damagedCarrot.png");
    private static final Image PROJECTILE_IMG = new Image("projectileImage/throwingCarrot.png");

    private boolean isAttacking = false;

    public Carrot(double x, double y, int health) {
        super(x, y, health);
        setWidth(NORMAL_IMG.getWidth()/5);
        setHeight(NORMAL_IMG.getHeight()/5);
    }

    @Override
    public void update() {
        // No update logic needed for this character
    }

    @Override
    public void render(GraphicsContext gc) {
        Image imageToRender = isAttacking ? ATTACK_IMG : NORMAL_IMG;
        if (isAttacking) {
            gc.drawImage(imageToRender, getX()-20, getY(), getWidth(), getHeight());
        } else {
            gc.drawImage(imageToRender, getX(), getY(), getWidth(), getHeight());
        }

    }


    @Override
    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public Image getProjectileImage() {
        return PROJECTILE_IMG;
    }
}
