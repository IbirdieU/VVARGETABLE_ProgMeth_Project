package entity.base;

import javafx.geometry.Rectangle2D;

public interface Collidable {
    void onCollision(GameObject other);
}
