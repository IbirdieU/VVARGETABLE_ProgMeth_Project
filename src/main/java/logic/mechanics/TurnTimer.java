package logic.mechanics;

public class TurnTimer {
    private double maxTime;
    private double currentTime;
    private boolean isTimeOut;

    public TurnTimer(double maxTimeSeconds) {
        this.maxTime = maxTimeSeconds;
        reset();
    }

    public void tick(double deltaTime) {
        if (currentTime > 0) {
            currentTime -= deltaTime;
            if (currentTime <= 0) {
                currentTime = 0;
                isTimeOut = true;
            }
        }
    }

    public void reset() {
        this.currentTime = maxTime;
        this.isTimeOut = false;
    }

    public int getCurrentTimeInt() {
        return (int) Math.ceil(currentTime);
    }

    public boolean isTimeOut() {
        return isTimeOut;
    }
}