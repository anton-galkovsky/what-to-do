package galanton.whattodo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;

abstract class CounterView extends MeasurableView implements Comparable {

    abstract protected void onDraw(Canvas canvas);
    abstract int getScaledCounter();
    abstract Object getData();
    abstract void onClick();
    abstract void updateTimes(long time);

    CounterView(int minViewSide, Context context) {
        super(minViewSide, context);
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
