package galanton.whattodo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

public class HorizontalContainer extends LinearLayout {

    private int actualWidth;
    private int dividerStep;

    public HorizontalContainer(Context context) {
        super(context);

        Drawable divider = context.getResources().getDrawable(R.drawable.empty_divider);
        setDividerDrawable(divider);
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        dividerStep = divider.getMinimumWidth();

        actualWidth = dividerStep;
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        actualWidth = dividerStep;
    }

    void addMeasurableView(CounterView counterView) {
        super.addView(counterView);
        actualWidth = getActualWidthWith(counterView);
    }

    int getActualWidthWith(CounterView counterView) {
        return actualWidth + dividerStep + counterView.getActualWidth();
    }

    int getActualWidth() {
        return actualWidth;
    }

    int getDividerStep() {
        return dividerStep;
    }
}
