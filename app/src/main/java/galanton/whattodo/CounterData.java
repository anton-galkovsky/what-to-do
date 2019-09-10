package galanton.whattodo;

import android.content.Intent;

interface CounterData {

    long getCounter();
    int getColor();

    void adjustParams(int color, long counter, boolean newColor);
    void onClick(long time);
    void updateTimes(long time);

    void putExtras(Intent intent);
}
