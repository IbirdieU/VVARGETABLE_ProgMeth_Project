package entity.status;

import entity.base.Character;
import entity.base.StatusEffect;

public class ToxicStatus extends StatusEffect {
    private double damagePerTurn;
    private boolean isFresh;

    public ToxicStatus(int duration, Double damagePerTurn) {
        super(duration,"/icon/statusIcon/poisonedStatus.png");
        this.damagePerTurn = damagePerTurn;
        this.isFresh = true;
    }

    /// first-turn damage blocking
    @Override
    public void activate(Character target) {
        System.out.println(target.getClass().getSimpleName() + " is Poisoned!");
        if (isFresh) {
            isFresh = false;
            return;
        }

        target.takeDamage(damagePerTurn);
    }
}