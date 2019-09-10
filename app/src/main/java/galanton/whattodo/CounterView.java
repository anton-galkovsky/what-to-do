package galanton.whattodo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;

abstract class CounterView extends MeasurableView implements Comparable {

    private int counterViewId;

    abstract protected void onDraw(Canvas canvas);
    abstract int getScaledCounter();
    abstract void adjustParams(CounterData counterData);

    CounterView(int counterViewId, int minViewSide, MainActivity activity) {
        super(minViewSide, activity);

        this.counterViewId = counterViewId;

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

    int getCounterViewId() {
        return counterViewId;
    }
}
