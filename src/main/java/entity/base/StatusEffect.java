package entity.base;

import entity.base.Character;
import javafx.scene.image.Image;

public abstract class StatusEffect {
    protected int duration;
    protected Image icon;

    public StatusEffect(int duration,String iconPath) {
        this.duration = duration;

        try {
            this.icon = new Image(getClass().getResourceAsStream(iconPath));
        } catch (Exception e) {
            System.out.println("Could not load status icon: " + iconPath);
            this.icon = null;
        }
    }

    public void onTurnStart(Character target) {
        if (duration > 0) {
            activate(target);
            duration--;
        }
    }

    public abstract void activate(Character target);

    public boolean isFinished() {
        return duration <= 0;
    }

    public Image getIcon() {
        return icon;
    }
}
