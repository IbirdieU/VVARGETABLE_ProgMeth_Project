package logic.skills;

import logic.GameManager;

public class Growth extends Skill {
    public Growth() {
        super("GROWTH", "/icon/skillIcon/growthOnion.png", 5);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().growth();
        System.out.println("Growth!");
    }
}
