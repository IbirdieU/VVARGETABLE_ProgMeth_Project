package logic.managers;

import logic.enums.PlayerTurn;
import logic.enums.TurnState;
import logic.mechanics.TurnTimer;
import logic.mechanics.Wind;

public class TurnManager {
    private PlayerTurn currentPlayerTurn;
    private TurnState currentTurnState;
    private TurnTimer turnTimer;
    private Wind wind;
    private int lastSecondPlayed = -1;

    public TurnManager() {
        this.currentPlayerTurn = PlayerTurn.PLAYER_ONE;
        this.currentTurnState = TurnState.READY;
        this.turnTimer = new TurnTimer(10.0);
        this.wind = new Wind();
    }

    public void update(double deltaTime) {
        if (currentTurnState == TurnState.READY || currentTurnState == TurnState.CHARGING) {
            turnTimer.tick(deltaTime);
            if (turnTimer.isTimeOut()) {
                currentTurnState = TurnState.CHANGING_TURN;
            }

            int remainingSeconds = turnTimer.getCurrentTimeInt();
            if (remainingSeconds <= 5 && remainingSeconds != lastSecondPlayed) {
                SoundManager.playTickingSound();
                lastSecondPlayed = remainingSeconds;
            }
        }
    }

    public void switchTurn() {
        currentPlayerTurn = (currentPlayerTurn == PlayerTurn.PLAYER_ONE) ? PlayerTurn.PLAYER_TWO : PlayerTurn.PLAYER_ONE;
        wind.onNewRound();
        turnTimer.reset();
        currentTurnState = TurnState.READY;
        lastSecondPlayed = -1;
    }

    public void reset() {
        this.currentPlayerTurn = PlayerTurn.PLAYER_ONE; //force to start from Carrot
        this.currentTurnState = TurnState.READY;
        this.turnTimer.reset();
        this.wind = new Wind();
        lastSecondPlayed = -1;
    }

    public void forceEndTurn() {
        this.currentTurnState = TurnState.CHANGING_TURN;
    }

    public PlayerTurn getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public TurnState getCurrentTurnState() {
        return currentTurnState;
    }

    public void setCurrentTurnState(TurnState state) {
        this.currentTurnState = state;
    }

    public Wind getWind() {
        return wind;
    }

    public TurnTimer getTurnTimer() {
        return turnTimer;
    }
}
