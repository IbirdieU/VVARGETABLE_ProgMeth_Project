package logic.mechanics;

public class PowerMechanic {
    private double currentPower;
    private final double MAX_POWER = 100;
    private final double MIN_POWER = 10;
    private final double POWER_CHARGE_RATE = 120;

    public PowerMechanic() {
        reset();
    }

    public void reset() {
        this.currentPower = MIN_POWER;
    }

    public void charge(double deltaTime) {
        currentPower += POWER_CHARGE_RATE * deltaTime;
        if (currentPower > MAX_POWER) {
            currentPower = MAX_POWER;
        }
    }

    public double getCurrentPower() {
        return currentPower;
    }

    public double getMaxPower() {
        return MAX_POWER;
    }

    public double getMinPower() {
        return MIN_POWER;
    }
}
