package entity.base;

import javafx.scene.canvas.GraphicsContext;

public abstract class Character extends GameObject implements Damageable,Collidable{
    protected int hp;
    protected int maxHp;
    public Character(double x, double y, int health) {
        super(x, y);
        this.hp = health;
        this.maxHp = health;
    }
    @Override
    public void takeDamage(int amount) {
        setHp(getHp()-amount);
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

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

}
