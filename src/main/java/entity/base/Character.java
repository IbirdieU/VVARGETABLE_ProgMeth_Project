package entity.base;

import javafx.scene.canvas.GraphicsContext;

public abstract class Character extends GameObject implements Damageable,Collidable{
    protected int hp;
    protected final static int MAXHP = 100;
    protected int damageTaken;
    protected boolean isDead = false;
    public Character(double x, double y, int health) {
        super(x, y);
        this.hp = health;
    }
    @Override
    public void takeDamage(int amount) {
        setHp(getHp()-amount);
        setDamageTaken(amount);
        setDead(getHp());
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp < 0) {
            this.hp = 0;
        } else {
            this.hp = hp;
        }
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
    public int getMaxHp() {
        return MAXHP;
    }


}
