package galanton.whattodo;

import android.content.Context;
import android.graphics.Color;

abstract class MeasurableView extends android.support.v7.widget.AppCompatButton {

    private int actualWidth;
    private int actualHeight;

    MeasurableView(int minViewSide, Context context) {
        super(context);

        setMinWidth(minViewSide);
        setMinimumWidth(minViewSide);
        setMinHeight(minViewSide);
        setMinimumHeight(minViewSide);
        actualWidth = 0;
        actualHeight = 0;
    }

    @Override
    public void setWidth(int pixels) {
        super.setWidth(pixels);
        actualWidth = pixels;
    }

    @Override
    public void setHeight(int pixels) {
        super.setHeight(pixels);
        actualHeight = pixels;
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
