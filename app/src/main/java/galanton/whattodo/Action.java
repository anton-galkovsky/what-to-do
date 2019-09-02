package galanton.whattodo;

import android.support.annotation.NonNull;

import java.io.Serializable;

class Action implements Serializable {

    private long allTime;
    private long lastUpdateTime;
    private int color;
    private boolean running;

    Action(ActionView actionView) {
        allTime = actionView.getAllTime();
        lastUpdateTime = actionView.getLastUpdateTime();
        color = actionView.getColor();
        running = actionView.isRunning();
    }

    Action(int color) {
        allTime = 0;
        lastUpdateTime = 0;
        this.color = color;
        running = false;
    }

    public long getAllTime() {
        return allTime;
    }

    public void setAllTime(long allTime) {
        this.allTime = allTime;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
