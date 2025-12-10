package logic.handlers;

import entity.base.Character;
import entity.status.StunStatus;
import entity.status.ToxicStatus;
import entity.unit.Projectile;
import entity.unit.Wall;
import logic.enums.TurnState;
import logic.managers.TurnManager;

public class CollisionHandler {

    private TurnManager turnManager;

    public CollisionHandler(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    public long handleCollision(Projectile projectile, Character opponent, Wall wall) {
        if (projectile.getHitBox().intersects(opponent.getHitBox())) {
            opponent.takeDamage(projectile.getDamage());

            if (projectile.isPoisonous()) {
                opponent.addStatusEffect(new ToxicStatus(projectile.getPoisonDuration(), projectile.getPoisonDamage()));
            }
            if (projectile.isStun()) {
                opponent.addStatusEffect(new StunStatus(2));
            }
            projectile.setDestroyed(true);
            turnManager.setCurrentTurnState(TurnState.HIT);
            return System.currentTimeMillis();
        } else if (projectile.getHitBox().intersects(wall.getHitBox())) {
            projectile.setDestroyed(true);
        }

        if (projectile.isDestroyed() && turnManager.getCurrentTurnState() != TurnState.HIT) {
            turnManager.setCurrentTurnState(TurnState.CHANGING_TURN);
        }


        return 0;
    }


}
