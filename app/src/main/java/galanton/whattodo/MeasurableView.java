package galanton.whattodo;

import android.content.Context;

class MeasurableView extends android.support.v7.widget.AppCompatTextView {

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

    int getActualHeight() {
        return actualHeight;
    }
}
