package logic.skills;

import logic.GameManager;

public class Toxic extends Skill {
    public Toxic() {
        super("TOXIC", "icon/skillIcon/toxicOnion.png", 4);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().toxic();
        System.out.println("Toxic!");
    }
}
