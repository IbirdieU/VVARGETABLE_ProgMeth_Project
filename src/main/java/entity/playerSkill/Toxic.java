package entity.playerSkill;

import logic.GameManager;
import entity.base.Skill;

public class Toxic extends Skill {
    public Toxic() {
        super("TOXIC", "icon/skillIcon/toxicOnion.png", 5);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().toxic();
    }
}
