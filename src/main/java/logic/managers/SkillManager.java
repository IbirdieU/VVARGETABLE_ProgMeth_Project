package logic.managers;

import gui.PlayerStatusPane;
import gui.SkillButton;
import logic.GameManager;
import logic.enums.PlayerTurn;
import logic.skills.*;
import logic.skills.Double;

public class SkillManager {
    private final GameManager gameManager;
    private final PlayerStatusPane playerStatusPane;

    private SkillButton p1Skill1, p1Skill2, p1Skill3;
    private SkillButton p2Skill1, p2Skill2, p2Skill3;

    public SkillManager(GameManager gameManager, PlayerStatusPane playerStatusPane) {
        this.gameManager = gameManager;
        this.playerStatusPane = playerStatusPane;
    }

    public void initializeSkills() {
        Skill skillC1 = new Double();
        Skill skillC2 = new Heal();
        Skill skillC3 = new Stun();
        Skill skillO1 = new Toxic();
        Skill skillO2 = new Growth();
        Skill skillO3 = new Shield();

        p1Skill1 = new SkillButton(skillC1, gameManager);
        p1Skill2 = new SkillButton(skillC2, gameManager);
        p1Skill3 = new SkillButton(skillC3, gameManager);

        p2Skill1 = new SkillButton(skillO1, gameManager);
        p2Skill2 = new SkillButton(skillO2, gameManager);
        p2Skill3 = new SkillButton(skillO3, gameManager);

        p1Skill1.setTurnActive(true);
        p1Skill2.setTurnActive(true);
        p1Skill3.setTurnActive(true);

        p2Skill1.setTurnActive(false);
        p2Skill2.setTurnActive(false);
        p2Skill3.setTurnActive(false);

        playerStatusPane.setSkills(p1Skill1, p1Skill2, p1Skill3, p2Skill1, p2Skill2, p2Skill3);
    }

    public void updateSkillButtons(PlayerTurn currentTurn,boolean isStun) {
        int cooldownReductionAmount = gameManager.isDoomedMode() ? 5 : 1;
        if (currentTurn == PlayerTurn.PLAYER_ONE) {
            for (int i = 0; i < cooldownReductionAmount; i++) {
                p1Skill1.onTurnStart();
                p1Skill2.onTurnStart();
                p1Skill3.onTurnStart();
            }
            if (isStun) {
                p1Skill1.setTurnActive(false);
                p1Skill2.setTurnActive(false);
                p1Skill3.setTurnActive(false);
            }
            p2Skill1.setTurnActive(false);
            p2Skill2.setTurnActive(false);
            p2Skill3.setTurnActive(false);
        } else {
            for (int i = 0; i < cooldownReductionAmount; i++) {
                p2Skill1.onTurnStart();
                p2Skill2.onTurnStart();
                p2Skill3.onTurnStart();
            }
            if (isStun) {
                p2Skill1.setTurnActive(false);
                p2Skill2.setTurnActive(false);
                p2Skill3.setTurnActive(false);
            }
            p1Skill1.setTurnActive(false);
            p1Skill2.setTurnActive(false);
            p1Skill3.setTurnActive(false);
        }
    }

    public void disableAllSkills() {
        p1Skill1.setTurnActive(false);
        p1Skill2.setTurnActive(false);
        p1Skill3.setTurnActive(false);
        p2Skill1.setTurnActive(false);
        p2Skill2.setTurnActive(false);
        p2Skill3.setTurnActive(false);
    }

    public void handleSkillUsage(PlayerTurn currentTurn) {
        if (currentTurn == PlayerTurn.PLAYER_ONE) {
            p1Skill1.setTurnActive(false);
            p1Skill2.setTurnActive(false);
            p1Skill3.setTurnActive(false);
        } else {
            p2Skill1.setTurnActive(false);
            p2Skill2.setTurnActive(false);
            p2Skill3.setTurnActive(false);
        }
    }
}
