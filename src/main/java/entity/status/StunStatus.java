package entity.status;

import entity.base.Character;
import entity.base.StatusEffect;

public class StunStatus extends StatusEffect {
    public StunStatus(int duration) {
        super(duration, "/icon/statusIcon/stunnedStatus.png");
    }

    @Override
    public void activate(Character target) {
        System.out.println(target.getClass().getSimpleName() + " is Dizzy! Skipping turn...");
    }

    public boolean shouldSkipTurn() {
        return true;
    }
}
