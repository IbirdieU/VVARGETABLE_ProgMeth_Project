package entity.base;

import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Character extends GameObject implements Damagedable{
    protected double hp;
    protected double maxHp;
    protected double damageTaken;
    protected boolean isDead = false;
    protected boolean isDamaged = false;
    protected boolean isAttacking = false;

    protected double damageMultiplier = 1.0;
    protected double projectileScale = 1.0;
    protected boolean isPoisonShot = false;

    private ArrayList<StatusEffect> activeStatusEffects = new ArrayList<>();

    public Character(double x, double y, int health) {
        super(x, y);
        this.hp = health;
        this.maxHp = health;

    }
    @Override
    public void takeDamage(double amount) {
        setHp(getHp()-amount);
        setDamageTaken(amount);
        setDead(getHp());
    }

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D(getX()+75,getY()+9,65,100);
    }

    @Override
    public void update() {

    }

    public void heal(int amount) {
        setHp(getHp()+amount);
    }

    public void toxic(){
        this.isPoisonShot = true;
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
    }

    public void addStatusEffect(StatusEffect effect) {
        activeStatusEffects.add(effect);
    }

    public void checkTurnStatus() {
        Iterator<StatusEffect> iterator = activeStatusEffects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();

            effect.onTurnStart(this);

            if (effect.isFinished()) {
                iterator.remove();
            }
        }
    }

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

    public double getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(double damageTaken) {
        this.damageTaken = damageTaken;
    }

    public void setDead(double hp) {
        if (hp <= 0) {
            isDead = true;
        } else {
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

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean isDamaged) {
        this.isDamaged = isDamaged;
    }

    public ArrayList<StatusEffect> getActiveStatusEffects() {
        return activeStatusEffects;
    }
}
