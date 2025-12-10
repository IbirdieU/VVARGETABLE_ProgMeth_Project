package entity.playerUnit;

import entity.base.Character;
import entity.base.StatusEffect;
import entity.status.ShieldStatus;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Onion extends Character {
    private static final Image NORMAL_IMG = new Image(Onion.class.getResourceAsStream("/playerImage/onion.png"));
    private static final Image ATTACK_IMG = new Image(Onion.class.getResourceAsStream("/playerImage/attackOnion.png"));
    private static final Image DAMAGED_IMG = new Image(Onion.class.getResourceAsStream("/playerImage/damagedOnion.png"));
    private static final Image PROJECTILE_IMG = new Image(Onion.class.getResourceAsStream("/unitImage/throwingOnion.png"));
    private boolean isPoisonShot = false;

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

    @Override
    public void takeDamage(double amount) {
        double calculatedAmount = amount;
        for (StatusEffect effect : getActiveStatusEffects()) {
            if (effect instanceof ShieldStatus) {
                calculatedAmount = amount * (1 - ((ShieldStatus) effect).getDamageReduction());
            }
        }
        super.takeDamage(calculatedAmount);
    }

    @Override
    public void toxic() {
        this.isPoisonShot = true;
    }

    @Override
    public void growth() {
        this.projectileScale = 2.0;
    }

    @Override
    public void resetBuffs() {
        super.resetBuffs();
        this.isPoisonShot = false;
    }

    public Image getProjectileImage() {
        return PROJECTILE_IMG;
    }

    @Override
    public double getLaunchY() {
        double realHeight = getProjectileImage().getHeight() * 0.2 * getProjectileScale();
        return getY() + (getHeight() / 2) - (realHeight / 2);
    }

    @Override
    public double getLaunchAngle() {
        return -120;
    }

    @Override
    public boolean isPoisonShot() {
        return isPoisonShot;
    }

    @Override
    public boolean isStunShot() {
        return false;
    }
}
