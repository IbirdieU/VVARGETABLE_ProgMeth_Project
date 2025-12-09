package entity.playerSkill;

import logic.GameManager;
import logic.Skill;

public class Toxic extends Skill {
    public Toxic() {
        super("TOXIC", "icon/skillIcon/toxicOnion.png", 5);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().toxic();
        System.out.println("Toxic!");
    }
}
