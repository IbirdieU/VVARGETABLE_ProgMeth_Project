package logic.skills;

import logic.GameManager;


public class Stun extends Skill {
    public Stun() {
        super("STUN", "icon/skillIcon/stunnedCarrot.png", 4);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().stunShot();
        System.out.println("Stun Shot Ready!");
    }
}