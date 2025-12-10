package logic.skills;

import logic.GameManager;
import logic.managers.TurnManager;

public class Heal extends Skill {
    public Heal() {
        super("HEAL", "icon/skillIcon/healCarrot.png", 4);
    }

    @Override
    public void activate(GameManager gameManager) {
        gameManager.getActivePlayer().heal(30);
        System.out.println("Used Heal!");

        gameManager.getTurnManager().forceEndTurn();
    }
}
