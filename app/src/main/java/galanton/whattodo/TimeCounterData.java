package galanton.whattodo;

import android.content.Intent;

import java.io.Serializable;

class TimeCounterData implements Serializable, CounterData {

    private long counter;
    private int color;
    private long lastUpdateTime;
    private boolean running;

    TimeCounterData(int color) {
        counter = 0;
        this.color = color;
        lastUpdateTime = -1;
    }

    boolean isRunning() {
        return running;
    }

    public long getCounter() {
        return counter;
    }

    public int getColor() {
        return color;
    }

    public void updateTimes(long time) {
        if (lastUpdateTime == -1) {
            lastUpdateTime = time;
        }
        if (running) {
            counter += time - lastUpdateTime;
        }
        lastUpdateTime = time;
    }

    @Override
    public void adjustParams(int color, long counter, boolean newColor) {
        if (newColor) {
            this.color = color;
        }
        this.counter = counter;
    }

    @Override
    public void onClick(long time) {
        updateTimes(time);
        running = !running;
    }

    @Override
    public void putExtras(Intent intent) {
        intent.putExtra("type", "time");
        intent.putExtra("counter", counter);
        intent.putExtra("color", color);
    }
}
