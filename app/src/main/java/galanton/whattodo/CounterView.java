package galanton.whattodo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

abstract class CounterView extends MeasurableView {

    private int counterViewId;
    private static String counterSource;

    abstract protected void onDraw(Canvas canvas);
    abstract int getScaledCounter();
    abstract void adjustParams(Bundle counterDataExtras);

    CounterView(int counterViewId, int minViewSide, MainActivity activity) {
        super(minViewSide, activity);

        this.counterViewId = counterViewId;

        setOnClickListener(activity);
        setOnLongClickListener(activity);
    }

    int negativeColor(int color) {
        return Color.rgb(255 - Color.red(color),
                255 - Color.green(color),
                255 - Color.blue(color));
    }

    int getCounterViewId() {
        return counterViewId;
    }

    static String getCounterSource() {
        return counterSource;
    }

    static void setCounterSource(String counterSource) {
        CounterView.counterSource = counterSource;
    }
}
