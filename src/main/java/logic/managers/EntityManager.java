package logic.managers;

import entity.base.GameObject;
import entity.playerUnit.Carrot;
import entity.playerUnit.Onion;
import entity.unit.Projectile;
import entity.unit.Wall;

import java.util.ArrayList;

public class EntityManager {
    private Carrot carrot;
    private Onion onion;
    private Wall wall;
    private final ArrayList<GameObject> allObjects;

    public EntityManager() {
        this.allObjects = new ArrayList<>();
    }

    public void initializeGameWorld(boolean isDoomedMode) {
        allObjects.clear();
        final double GROUND_Y = 660;

        this.carrot = new Carrot(20, 0, 100);
        this.onion = new Onion(1050, 0, 100);
        if (!isDoomedMode) {
            this.wall = new Wall(600, 327, 76, 393);
        } else {
            this.wall = new Wall(1, 1, 1, 1);
        }
        this.carrot.setY(GROUND_Y - this.carrot.getHeight());
        this.onion.setY(GROUND_Y - this.onion.getHeight());

        allObjects.add(carrot);
        allObjects.add(onion);
        allObjects.add(wall);
    }

    public Carrot getCarrot() {
        return carrot;
    }

    public Onion getOnion() {
        return onion;
    }

    public Wall getWall() {
        return wall;
    }

    public ArrayList<GameObject> getAllObjects() {
        return allObjects;
    }

    public void addProjectile(Projectile p) {
        allObjects.add(p);
    }

    public void cleanupDestroyed(Projectile currentProjectile) {
        if (currentProjectile != null && currentProjectile.isDestroyed()) {
            allObjects.remove(currentProjectile);
        }
    }
}
