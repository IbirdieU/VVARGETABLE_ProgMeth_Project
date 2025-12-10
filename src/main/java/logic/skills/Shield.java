package logic.skills;

import logic.GameManager;
import entity.status.ShieldStatus;

public class Shield extends Skill {
    public Shield() {
        super("SHIELD", "icon/skillIcon/shieldedOnion.png", 4);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().addStatusEffect(new ShieldStatus(2));
        System.out.println("Shield Activated!");
    }
}