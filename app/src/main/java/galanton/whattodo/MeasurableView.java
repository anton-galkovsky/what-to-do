package galanton.whattodo;

import android.content.Context;

class MeasurableView extends android.support.v7.widget.AppCompatTextView {

    private int actualWidth;

    MeasurableView(int minViewSide, Context context) {
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

    int getActualWidth() {
        return actualWidth;
    }
}
