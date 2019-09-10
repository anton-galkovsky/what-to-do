package galanton.whattodo;

import android.graphics.Canvas;
import android.graphics.Paint;

class ClickCounterView extends CounterView {

    private int color;
    private long counter;

    private Paint fillPaint;
    private Paint textPaint;

    ClickCounterView(int counterViewId, ClickCounterData clickCounterData, int minViewSide, MainActivity activity) {
        super(counterViewId, minViewSide, activity);

        color = clickCounterData.getColor();
        counter = clickCounterData.getCounter();

        fillPaint = new Paint();
        fillPaint.setColor(clickCounterData.getColor());
        fillPaint.setStyle(Paint.Style.FILL);
        textPaint = new Paint();
        textPaint.setColor(negativeColor(clickCounterData.getColor()));
        textPaint.setTextSize(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), fillPaint);
        canvas.drawText("" + counter, 0, 100, textPaint);
    }

    @Override
    int getScaledCounter() {
        return (int) (counter * 1000);
    }

    @Override
    void adjustParams(CounterData counterData) {
        if (counterData.getColor() != color) {
            color = counterData.getColor();
            fillPaint.setColor(color);
            textPaint.setColor(negativeColor(color));
        }
        counter = counterData.getCounter();
    }
}
