package galanton.whattodo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

public class HorizontalContainer extends LinearLayout {

    private int actualWidth;
    private int dividerStep;

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

    void addActionView(ActionView actionView) {
        super.addView(actionView);
        actualWidth = getActualWidthWith(actionView);
    }

    int getActualWidthWith(ActionView actionView) {
        return actualWidth + dividerStep + actionView.getActualWidth();
    }
}
