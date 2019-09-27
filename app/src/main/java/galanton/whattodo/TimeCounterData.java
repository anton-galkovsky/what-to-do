package galanton.whattodo;

import android.os.Bundle;

import java.io.Serializable;

class TimeCounterData implements Serializable, CounterData {

    static final long serialVersionUID = -7424958886620172413L;

    private long counter;
    private long todayCounter;
    private int color;
    private long lastUpdateTime;
    private boolean running;

    TimeCounterData(int color) {
        counter = 0;
        todayCounter = 0;
        this.color = color;
        lastUpdateTime = -1;
    }

    public void updateTimes(long time) {
        if (lastUpdateTime == -1) {
            lastUpdateTime = time;
        }
        if (running) {
            counter += time - lastUpdateTime;
            todayCounter += time - lastUpdateTime;
        }
        lastUpdateTime = time;
    }

    @Override
    public void adjustParams(int color, long counterInc) {
        this.color = color;
        todayCounter += counterInc;
        counter += counterInc;
    }

    @Override
    public void onClick(long time) {
        updateTimes(time);
        running = !running;
    }

    @Override
    public void onSync() {
        todayCounter = 0;
    }

    @Override
    public Bundle getExtras() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "time");
        bundle.putLong(ScreenType.ALL_TIME.value, counter);
        bundle.putLong(ScreenType.DAY.value, todayCounter);
        bundle.putInt("color", color);
        bundle.putBoolean("running", running);
        return bundle;
    }
}
