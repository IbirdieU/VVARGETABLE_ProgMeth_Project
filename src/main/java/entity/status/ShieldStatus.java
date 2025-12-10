package entity.status;

import entity.base.Character;
import entity.base.StatusEffect;

public class ShieldStatus extends StatusEffect {
    private final double damageReduction = 0.6;

    public ShieldStatus(int duration) {
        super(duration, "/icon/statusIcon/shieldedStatus.png");
    }

    @Override
    public void activate(Character target) {
        System.out.println("Shielded UP");
    }

    public double getDamageReduction() {
        return damageReduction;
    }
}