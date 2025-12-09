package entity.playerunit;

import entity.base.Character;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Onion extends Character {
    private static final Image NORMAL_IMG = new Image("playerImage/onion.png");
    private static final Image ATTACK_IMG = new Image("playerImage/attackOnion.png");
    private static final Image DAMAGED_IMG = new Image("playerImage/damagedOnion.png");
    private static final Image PROJECTILE_IMG = new Image("unitImage/throwingOnion.png");
    public Onion(double x, double y, int health) {
        super(x, y, health);
        setWidth(NORMAL_IMG.getWidth()/5);
        setHeight(NORMAL_IMG.getHeight()/5);
    }


    @Override
    public void render(GraphicsContext gc) {
        Image imageToRender;
        if (isShowingDamaged()) {
            imageToRender = DAMAGED_IMG;
        } else if (isAttacking) {
            imageToRender = ATTACK_IMG;
        } else {
            imageToRender = NORMAL_IMG;
        }
        gc.drawImage(imageToRender, getX(), getY(), getWidth(), getHeight());
    }

    public Image getProjectileImage() {
        return PROJECTILE_IMG;
    }
}
