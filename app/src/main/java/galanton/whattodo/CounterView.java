package galanton.whattodo;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;

abstract class CounterView extends MeasurableView implements Comparable {

    abstract protected void onDraw(Canvas canvas);
    abstract int getScaledCounter();
    abstract Object getData();
    abstract void onClick();
    abstract void updateTimes(long time);
    abstract void putExtras(Intent intent);
    abstract void setCounter(long counter);
    abstract void setColor(int color);

    CounterView(int minViewSide, MainActivity activity) {
        super(minViewSide, activity);

        setOnClickListener(activity);
        setOnLongClickListener(activity);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return ((CounterView) o).getScaledCounter() - getScaledCounter();
    }

    int negativeColor(int color) {
        return Color.rgb(255 - Color.red(color),
                255 - Color.green(color),
                255 - Color.blue(color));
    }

}
