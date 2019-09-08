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

    int compareTo(ClickCounterData o) {
        return (int) (o.counter - counter);
    }

    void increaseCounter() {
        counter++;
    }
}
