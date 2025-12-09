package logic;

import java.util.Random;

public class Wind {
    private double strength; // positive for right, negative for left
    private final double MAX_STRENGTH = 100; // Adjust as needed
    private Random random;
    private int direction;
    public Wind() {
        this.random = new Random();
        randomize();
    }

    public void randomize() {
        // Generates a strength between -MAX_STRENGTH and +MAX_STRENGTH
        this.strength = (random.nextDouble() * 2 - 1) * MAX_STRENGTH;;
    }

    public double getStrength() {
        return strength;
    }

    public int getDirection() {
        if (strength == 0) {
            return 0;
        }
        return strength > 0 ? 1 : -1;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public double getStrengthMagnitude() {
        return Math.abs(strength);
    }
}
