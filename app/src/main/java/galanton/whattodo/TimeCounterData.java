package galanton.whattodo;

import java.io.Serializable;

class TimeCounterData implements Serializable {

    private long counter;
    private int color;
    private long lastUpdateTime;
    private boolean running;

    TimeCounterData(int color) {
        counter = 0;
        this.color = color;
    }

    void changeRunning() {
        long time = System.currentTimeMillis();
        if (running) {
            counter += time - lastUpdateTime;
        }
        lastUpdateTime = time;
        running = !running;
    }

    boolean isRunning() {
        return running;
    }

    long getCounter() {
        return counter;
    }

    int getColor() {
        return color;
    }

    void updateTimes(long time) {
        if (running) {
            counter += time - lastUpdateTime;
            lastUpdateTime = time;
        }
    }

    int compareTo(TimeCounterData o) {
        return (int) (o.counter - counter);
    }
}
