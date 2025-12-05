package entity.unit;

import entity.base.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Projectile extends GameObject {
    public Projectile(double x, double y,String imageURL) {
        super(x, y);
        setImage(imageURL);
    }
    public void setImage(String imageURL) {
        Image projectileImage = new Image(imageURL);
        this.setWidth(projectileImage.getWidth());
        this.setHeight(projectileImage.getHeight());
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }
}
