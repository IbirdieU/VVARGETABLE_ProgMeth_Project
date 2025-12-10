package entity.base;

import entity.status.StunStatus;
import entity.unit.Projectile;
import entity.unit.ProjectileStats;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import logic.managers.SoundManager;
import logic.mechanics.Wind;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Character extends GameObject implements Damagedable{
    protected double hp;
    protected double maxHp;
    protected boolean isDead = false;

    private static final double DAMAGED_STATE_DURATION_S = 1.0; // 1 second
    private double damagedStateTimer = 0; // Timer for how long to show damaged sprite

    protected boolean isAttacking = false;

    protected double damageMultiplier = 1.0;
    protected double projectileScale = 1.0;

    private final ArrayList<StatusEffect> activeStatusEffects = new ArrayList<>();

    public Character(double x, double y, int health) {
        super(x, y);
        this.hp = health;
        this.maxHp = health;

    }
    @Override
    public void takeDamage(double amount) {
        setHp(getHp()-amount);
        setDead(getHp());
        this.damagedStateTimer = DAMAGED_STATE_DURATION_S; // Set the timer when damage is taken
        SoundManager.playStrikeSound();
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D(getX()+75,getY()+12,65,100);
    }
    
    @Override
    public void update(double deltaTime) { // Update the timer
        if (damagedStateTimer > 0) {
            damagedStateTimer -= deltaTime;
        }
    }
    public void heal(int amount) {
        setHp(getHp()+amount);
    }

    public void toxic(){

    }

    public void stunShot() {

    }

    public void doubleDamage(){ }

    public void growth(){ }

    public void resetBuffs() {
        this.damageMultiplier = 1.0;
        this.projectileScale = 1.0;
    }

    public void addStatusEffect(StatusEffect effect) {
        activeStatusEffects.add(effect);
    }

    public boolean checkTurnStatus() {
        boolean shouldSkipTurn = false;

        Iterator<StatusEffect> iterator = activeStatusEffects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();

            //Stun
            if (effect instanceof StunStatus) {
                if (((StunStatus) effect).shouldSkipTurn()) {
                    shouldSkipTurn = true;
                }
            }

            effect.onTurnStart(this);

            //Toxic
            if (effect.isFinished()) {
                iterator.remove();
            }
        }

        return shouldSkipTurn;
    }

    public Projectile shoot(double power, Wind wind,boolean isDoomedMode) {
        double modeFactor = isDoomedMode ? 0.6 : 0.3;
        double totalDamage = power * modeFactor * damageMultiplier;
        ProjectileStats stats = new ProjectileStats(totalDamage, projectileScale, isPoisonShot(), isStunShot());

        Image projectileImage = getProjectileImage();
        double realWidth = projectileImage.getWidth() * 0.2 * stats.getScale();
        double spawnCenterX = getX() + (getWidth() / 2);
        double startX = spawnCenterX - (realWidth / 2);

        Projectile projectile = new Projectile(startX, getLaunchY(), getProjectileImage(), getLaunchAngle(), power, wind, stats);
        resetBuffs();
        return projectile;
    }

    public abstract double getLaunchX();
    public abstract double getLaunchY();
    public abstract double getLaunchAngle();
    public abstract Image getProjectileImage();
    public abstract boolean isPoisonShot();
    public abstract boolean isStunShot();


    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = Math.max(0, Math.min(hp, this.maxHp));
    }

    public double getMaxHp() {
        return maxHp;
    }

    public void setDead(double hp) {
        isDead = hp <= 0;
    }
    public boolean isDead() {
       return this.isDead;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public double getProjectileScale() {
        return projectileScale;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean isShowingDamaged() {
        return damagedStateTimer > 0;
    }

    public ArrayList<StatusEffect> getActiveStatusEffects() {
        return activeStatusEffects;
    }
}
