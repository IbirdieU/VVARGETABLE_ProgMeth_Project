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

    @Override
    public void activate(Character target) {
        if (isFresh) {
            isFresh = false;
            return;
        }

        target.takeDamage(damagePerTurn);
    }
}