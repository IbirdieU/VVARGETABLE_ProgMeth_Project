package entity.playerSkill;

import logic.GameManager;
import entity.base.Skill;

public class Heal extends Skill {
    public Heal() {
        super("HEAL", "icon/skillIcon/healCarrot.png", 5);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().heal(30);
        System.out.println("Used Heal!");
    }
}
