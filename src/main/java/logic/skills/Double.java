package logic.skills;

import logic.GameManager;

public class Double extends Skill {
    public Double() {
        super("DOUBLE", "/icon/skillIcon/doubleCarrot.png", 5);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().doubleDamage();
        System.out.println("Double Damage!");
    }
}
