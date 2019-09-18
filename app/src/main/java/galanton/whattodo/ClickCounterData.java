package galanton.whattodo;

import android.content.Intent;

import java.io.Serializable;

class ClickCounterData implements Serializable, CounterData {

    static final long serialVersionUID = -4476545837915515477L;

    private long counter;
    private int color;

    ClickCounterData(int color) {
        counter = 0;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public long getCounter() {
        return counter;
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
        counter++;
    }

    @Override
    public void updateTimes(long time) {
    }

    @Override
    public void putExtras(Intent intent) {
        intent.putExtra("type", "click");
        intent.putExtra("counter", counter);
        intent.putExtra("color", color);
    }
}
