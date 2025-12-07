package entity.base;

import javafx.geometry.Rectangle2D;

public abstract class Character extends GameObject implements Damagedable{
    protected int hp;
    protected int maxHp;
    protected int damageTaken;
    protected boolean isDead = false;
    protected boolean isAttacking = false;
    protected boolean isDamaged = false;
    protected long damagedStartTime;
    protected static final long DAMAGED_DURATION = 800; // 0.5 seconds

    public Character(double x, double y, int health) {
        super(x, y);
        this.hp = health;
        this.maxHp = health;
    }
    @Override
    public void takeDamage(int amount) {
        setHp(getHp()-amount);
        setDamageTaken(amount);
        setDead(getHp());
        isDamaged = true;
        damagedStartTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (isDamaged && System.currentTimeMillis() - damagedStartTime > DAMAGED_DURATION) {
            isDamaged = false;
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp < 0) {
            this.hp = 0;
        } else if (hp > this.maxHp) {
            this.hp = this.maxHp;
        } else {
            this.hp = hp;
        }
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(int damageTaken) {
        this.damageTaken = damageTaken;
    }
    public void setDead(int hp) {
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

    @Override
    public Rectangle2D getHitBox() {
        return new Rectangle2D(getX()+75,getY()+9,65,100);
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }
}
