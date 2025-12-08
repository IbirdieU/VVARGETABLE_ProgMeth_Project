package entity.base;

import javafx.geometry.Rectangle2D;

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

    protected int poisonDuration = 0;
    protected int poisonDamage = 0;

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
        setDamaged(true);
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

    public void applyPoison(int duration, int damagePerTurn) {
        this.poisonDuration = duration;
        this.poisonDamage = damagePerTurn;
    }

    public void checkTurnStatus() {
        if (poisonDuration > 0) {
            takeDamage(poisonDamage);
            poisonDuration--;
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

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean isDamaged) {
        this.isDamaged = isDamaged;
    }
}
