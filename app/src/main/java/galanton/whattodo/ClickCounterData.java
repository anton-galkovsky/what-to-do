package galanton.whattodo;

import java.io.Serializable;

class ClickCounterData implements Serializable {

    private long counter;
    private int color;

    ClickCounterData(int color) {
        counter = 0;
        this.color = color;
    }

    int getColor() {
        return color;
    }

    long getCounter() {
        return counter;
    }

    void increaseCounter() {
        counter++;
    }

    void setCounter(long counter) {
        this.counter = counter;
    }

    void setColor(int color) {
        this.color = color;
    }
}
