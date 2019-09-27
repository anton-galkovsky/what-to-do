package galanton.whattodo;

import android.os.Bundle;

import java.io.Serializable;

class ClickCounterData implements Serializable, CounterData {

    static final long serialVersionUID = -4476545837915515477L;

    private long counter;
    private long todayCounter;
    private int color;

    ClickCounterData(int color) {
        counter = 0;
        todayCounter = 0;
        this.color = color;
    }

    @Override
    public void adjustParams(int color, long counterInc) {
        this.color = color;
        todayCounter += counterInc;
        counter += counterInc;
    }

    @Override
    public void onClick(long time) {
        counter++;
        todayCounter++;
    }

    @Override
    public void onSync() {
        todayCounter = 0;
    }

    @Override
    public void updateTimes(long time) {
    }

    @Override
    public Bundle getExtras() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "click");
        bundle.putLong(ScreenType.ALL_TIME.value, counter);
        bundle.putLong(ScreenType.DAY.value, todayCounter);
        bundle.putInt("color", color);
        return bundle;
    }
}
