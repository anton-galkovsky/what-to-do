package galanton.whattodo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class HorizontalContainer extends LinearLayout {

    private ArrayList<MeasurableView> views;
    private int dividerStep;

    public HorizontalContainer(Context context) {
        super(context);

        Drawable divider = context.getResources().getDrawable(R.drawable.empty_divider);
        setDividerDrawable(divider);
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        dividerStep = divider.getMinimumWidth();

        setOrientation(LinearLayout.HORIZONTAL);

        views = new ArrayList<>();
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        views.clear();
    }

    void addMeasurableView(MeasurableView view) {
        super.addView(view);
        views.add(view);
    }

    int getActualWidthWith(MeasurableView view) {
        return getActualWidth() + dividerStep + view.getActualWidth();
    }

    int getActualWidth() {
        int width = dividerStep;
        for (MeasurableView view : views) {
            width += view.getActualWidth() + dividerStep;
        }
        return width;
    }

    int getDividerStep() {
        return dividerStep;
    }
}
