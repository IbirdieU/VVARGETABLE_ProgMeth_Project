package entity.unit;

public class ProjectileStats {
    private final double damage;
    private final double scale;
    private final boolean isPoisonous;
    private final boolean isStun;
    private final int poisonDuration;
    private final double poisonDamage;

    public ProjectileStats(double damage, double scale, boolean isPoisonous, boolean isStun) {
        this.damage = damage;
        this.scale = scale;
        this.isPoisonous = isPoisonous;
        this.isStun = isStun;

        if (isPoisonous) {
            this.poisonDuration = 2;
            this.poisonDamage = 10;
        } else {
            this.poisonDuration = 0;
            this.poisonDamage = 0;
        }
    }

    public double getDamage() {
        return damage;
    }

    public double getScale() {
        return scale;
    }

    public boolean isPoisonous() {
        return isPoisonous;
    }

    public boolean isStun() {
        return isStun;
    }

    public int getPoisonDuration() {
        return poisonDuration;
    }

    public double getPoisonDamage() {
        return poisonDamage;
    }
}
