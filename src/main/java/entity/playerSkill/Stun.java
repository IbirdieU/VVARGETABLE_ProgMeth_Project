package entity.playerSkill;

import logic.GameManager;
import logic.Skill;

public class Stun extends Skill {
    public Stun() {
        super("STUN", "icon/skillIcon/stunnedCarrot.png", 5);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().stunShot();
        System.out.println("Stun Shot Ready!");
    }
}
