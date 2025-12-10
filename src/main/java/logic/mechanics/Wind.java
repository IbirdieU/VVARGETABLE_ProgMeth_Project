package logic.mechanics;

import java.util.Random;

public class Wind {
    private double strength; // positive for right, negative for left
    private final double MAX_STRENGTH = 100; // Adjust as needed
    private Random random;
    private int duration; // in rounds

    public Wind() {
        this.random = new Random();
        randomize(); // Initialize with random wind
    }

    /**
     * Should be called at the start of each new round.
     * Decrements the duration and randomizes the wind if the duration has expired.
     */
    public void onNewRound() {
        duration--;
        if (duration <= 0) {
            randomize();
        }
    }

    private void randomize() {
        // Generates a strength between -MAX_STRENGTH and +MAX_STRENGTH
        this.strength = (random.nextDouble() * 2 - 1) * MAX_STRENGTH;
        // Generates a duration of 4 or 5 rounds
        this.duration = random.nextInt(3) + 3;
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

    public double getStrengthMagnitude() {
        return Math.abs(strength);
    }

    public int getRemainingDuration() {
        return duration;
    }
}
