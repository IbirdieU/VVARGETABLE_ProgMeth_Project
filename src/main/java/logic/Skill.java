package logic;

import logic.GameManager;
import javafx.scene.image.Image;

public abstract class Skill {
    protected String name;
    protected Image icon;
    protected int maxCooldown;
    protected int currentCooldown;
    protected boolean isActive;

    public Skill(String name, String iconPath, int cooldown) {
        this.name = name;
        this.maxCooldown = cooldown;
        this.currentCooldown = 0;

        try {
            this.icon = new Image(ClassLoader.getSystemResourceAsStream(iconPath));
        } catch (Exception e) {
            System.out.println("Error loading skill icon: " + iconPath);
        }
    }

    public abstract void activate(GameManager gameManager);

    public void reduceCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    public void use(GameManager gm) {
        if (isReady()) {
            activate(gm);       // ทำงาน
            currentCooldown = maxCooldown; // เริ่มนับถอยหลัง
        }
    }

    public boolean isReady() {
        return currentCooldown <= 0;
    }

    public Image getIcon() { return icon; }
    public int getCurrentCooldown() { return currentCooldown; }
    public String getName() { return name; }
}
