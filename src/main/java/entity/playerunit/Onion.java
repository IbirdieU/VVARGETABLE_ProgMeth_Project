package entity.playerunit;

import entity.base.Character;
import entity.base.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Onion extends Character {
    private static final Image IMG = new Image("playerImage/onion.png");
    public Onion(double x, double y, int health) {
        super(x, y, health);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(IMG,getX(),getY(),getWidth(),getHeight());
    }


    @Override
    public void onCollision(GameObject other) {

    }
}
