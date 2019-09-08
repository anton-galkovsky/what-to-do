package galanton.whattodo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

public class HorizontalContainer extends LinearLayout {

    private int actualWidth;
    private static int dividerStep;

    public HorizontalContainer(Context context) {
        super(context);
        actualWidth = 0;
        setOrientation(LinearLayout.HORIZONTAL);
        Drawable divider = context.getResources().getDrawable(R.drawable.empty_divider);
        setDividerDrawable(divider);
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        dividerStep = divider.getMinimumWidth();
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        actualWidth = dividerStep;
    }

    void addMeasurableView(MeasurableView measurableView) {
        super.addView(measurableView);
        actualWidth = getActualWidthWith(measurableView);
    }

    int getActualWidthWith(MeasurableView measurableView) {
        return actualWidth + dividerStep + measurableView.getActualWidth();
    }

    static int getDividerStep() {
        return dividerStep;
    }
}
