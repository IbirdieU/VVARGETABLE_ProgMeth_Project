package entity.status;

import entity.base.Character;
import entity.base.StatusEffect;

public class ShieldStatus extends StatusEffect {
    private double damageReduction;

    public ShieldStatus(int duration) {
        super(duration, "/icon/statusIcon/shieldedStatus.png");
        this.damageReduction = 0.5;
    }

    @Override
    public void activate(Character target) {
        System.out.println("Shielded UP");
    }

    public double getDamageReduction() {
        return damageReduction;
    }
}