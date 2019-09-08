package galanton.whattodo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;

import java.io.Serializable;

abstract class CounterView extends android.support.v7.widget.AppCompatButton implements Serializable, Comparable {

    private int actualWidth;

    abstract protected void onDraw(Canvas canvas);
    abstract int getScaledCounter();
    abstract Object getData();
    abstract void onClick();
    abstract void updateTimes(long time);

    CounterView(int minViewSide, Context context) {
        super(context);

        setMinWidth(minViewSide);
        setMinimumWidth(minViewSide);
        setMinHeight(minViewSide);
        setMinimumHeight(minViewSide);
        actualWidth = 0;
    }

    @Override
    public void setWidth(int pixels) {
        super.setWidth(pixels);
        actualWidth = pixels;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return ((CounterView) o).getScaledCounter() - getScaledCounter();
    }

    int getActualWidth() {
        return actualWidth;
    }

    int negativeColor(int color) {
        return Color.rgb(255 - Color.red(color),
                255 - Color.green(color),
                255 - Color.blue(color));
    }

}
