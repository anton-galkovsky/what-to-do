package galanton.whattodo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

class ClickCounterView extends MeasurableView implements Comparable {

    // Counter part
    private ClickCounterData clickCounterData;

    // View part
    private Paint fillPaint;
    private Paint textPaint;

    ClickCounterView(ClickCounterData clickCounterData, int minViewSide, MainActivity activity) {
        super(minViewSide, activity);

        this.clickCounterData = clickCounterData;                     // or copy constructor???

        fillPaint = new Paint();
        fillPaint.setColor(clickCounterData.getColor());
        fillPaint.setStyle(Paint.Style.FILL);
        textPaint = new Paint();
        textPaint.setColor(negativeColor(clickCounterData.getColor()));
        textPaint.setTextSize(100);

        setOnClickListener(activity);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), fillPaint);
        canvas.drawText("" + clickCounterData.getCounter(), 0, 100, textPaint);
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return clickCounterData.compareTo(((ClickCounterView) o).clickCounterData);
    }

    long getCounter() {
        return clickCounterData.getCounter();
    }

    ClickCounterData getData() {
        return clickCounterData;
    }

    void onClick() {
        clickCounterData.increaseCounter();
    }
}