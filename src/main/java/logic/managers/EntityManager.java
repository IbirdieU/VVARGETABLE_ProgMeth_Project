package logic.managers;

import entity.base.GameObject;
import entity.playerunit.Carrot;
import entity.playerunit.Onion;
import entity.unit.Projectile;
import entity.unit.Wall;

import java.util.ArrayList;
import java.util.Iterator;

public class EntityManager {
    private Carrot carrot;
    private Onion onion;
    private Wall wall;
    private ArrayList<GameObject> allObjects;

    public EntityManager() {
        this.allObjects = new ArrayList<>();
    }

    public void initializeGameWorld() {
        allObjects.clear();
        final double GROUND_Y = 660;

        this.carrot = new Carrot(20, 0, 100);
        this.onion = new Onion(1050, 0, 100);
        this.wall = new Wall(600, 327, 72, 393);

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
