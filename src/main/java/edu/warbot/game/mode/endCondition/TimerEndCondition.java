package edu.warbot.game.mode.endCondition;

import edu.warbot.game.WarGame;

public class TimerEndCondition extends AbstractEndCondition{

    protected long curentTick;
    private final double lastTick;

    public TimerEndCondition(WarGame game, long lastTick) {
        super(game);
        this.lastTick = lastTick;
        resetCurentTick();
    }

    @Override
    public void doAfterEachTick() {
        incrementCurentTick();
    }

    @Override
    public boolean isGameEnded() {
        if(curentTick >= lastTick)
            return true;

        return false;
    }

    public void incrementCurentTick() {
        curentTick++;
    }

    public void resetCurentTick() {
        curentTick = 0;
    }

}