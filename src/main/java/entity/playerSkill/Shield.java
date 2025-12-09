package entity.playerSkill;

import entity.status.ShieldStatus;
import logic.GameManager;
import logic.Skill;

public class Shield extends Skill {
    public Shield() {
        super("SHIELD", "icon/skillIcon/shieldedOnion.png", 5);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().addStatusEffect(new ShieldStatus(3));
        System.out.println("Shield Activated!");
    }
}
