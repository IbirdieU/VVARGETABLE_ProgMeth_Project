package entity.base;

import entity.status.ShieldStatus;
import entity.status.StunStatus;
import javafx.geometry.Rectangle2D;

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
    protected boolean isPoisonShot = false;
    protected boolean isStunShot = false;

    private ArrayList<StatusEffect> activeStatusEffects = new ArrayList<>();

    public Character(double x, double y, int health) {
        super(x, y);
        this.hp = health;
        this.maxHp = health;

    }
    @Override
    public void takeDamage(double amount) {
        double reduction = 1.0;

        for (StatusEffect effect : getActiveStatusEffects()) {
            if (effect instanceof ShieldStatus) {
                reduction *= ((ShieldStatus) effect).getDamageReduction();
            }
        }

        double finalDamage = amount * reduction;
        setHp(getHp() - finalDamage);
        setDead(getHp());
        this.damagedStateTimer = DAMAGED_STATE_DURATION_S; // Set the timer when damage is taken
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
        this.isPoisonShot = true;
    }

    public void stunShot() {
        this.isStunShot = true;
    }

    public void doubleDamage(){
        this.damageMultiplier = 2.0;
    }

    public void growth(){
        this.projectileScale = 2.0;
    }

    public void resetBuffs() {
        this.damageMultiplier = 1.0;
        this.projectileScale = 1.0;
        this.isPoisonShot = false;
        this.isStunShot = false;
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

    public abstract double getLaunchX();
    public abstract double getLaunchY();
    public abstract double getLaunchAngle();


    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        if (hp < 0) {
            this.hp = 0;
        } else if (hp > this.maxHp) {
            this.hp = this.maxHp;
        } else {
            this.hp = hp;
        }
    }

    public double getMaxHp() {
        return maxHp;
    }

    public void setDead(double hp) {
        if (hp <= 0) {
            isDead = true;
        }
        else {
            isDead = false;
        }
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

    public boolean isPoisonShot() {
        return isPoisonShot;
    }

    public boolean isStunShot() {
        return isStunShot;
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
