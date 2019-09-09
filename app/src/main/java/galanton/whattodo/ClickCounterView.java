package galanton.whattodo;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;

class ClickCounterView extends CounterView {

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), fillPaint);
        canvas.drawText("" + clickCounterData.getCounter(), 0, 100, textPaint);
    }

    @Override
    int getScaledCounter() {
        return (int) (clickCounterData.getCounter() * 1000);
    }

    @Override
    ClickCounterData getData() {
        return clickCounterData;
    }

    @Override
    void onClick() {
        clickCounterData.increaseCounter();
    }

    @Override
    void updateTimes(long time) {
    }

    @Override
    void putExtras(Intent intent) {
        intent.putExtra("type", "click");
        intent.putExtra("color", clickCounterData.getColor());
        intent.putExtra("counter", clickCounterData.getCounter());
    }

    @Override
    void setCounter(long counter) {
        clickCounterData.setCounter(counter);
    }

    @Override
    void setColor(int color) {
        clickCounterData.setColor(color);
        fillPaint.setColor(color);
        textPaint.setColor(negativeColor(color));
    }
}
