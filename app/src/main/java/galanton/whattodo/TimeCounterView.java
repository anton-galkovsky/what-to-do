package galanton.whattodo;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;

class TimeCounterView extends CounterView {

    private int color;
    private long counter;
    private boolean running;

    private Paint fillPaint;
    private Paint strokePaint;
    private Paint textPaint;

    TimeCounterView(int counterViewId, TimeCounterData timeCounterData, int minViewSide, MainActivity activity) {
        super(counterViewId, minViewSide, activity);

        color = timeCounterData.getColor();
        counter = timeCounterData.getCounter();
        running = timeCounterData.isRunning();

        fillPaint = new Paint();
        fillPaint.setColor(color);
        fillPaint.setStyle(Paint.Style.FILL);
        strokePaint = new Paint();
        strokePaint.setColor(negativeColor(color));
        strokePaint.setStyle(Paint.Style.FILL);
        textPaint = new Paint();
        textPaint.setColor(negativeColor(color));
        textPaint.setTextSize(70);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), fillPaint);
        if (running) {
            int step = 20;
            for (int i = 0; i - step < getWidth(); i += step) {
                for (int j = 0; j - step < getHeight(); j += step) {
                    canvas.drawCircle(i, j, 3, strokePaint);
                }
            }
        }
        canvas.drawText("" + counter / 1000, 0, 70, textPaint);
    }

    @Override
    int getScaledCounter() {
        return (int) (counter / 1000);
    }

    @Override
    void adjustParams(CounterData counterData) {
        if (counterData.getColor() != color) {
            color = counterData.getColor();
            fillPaint.setColor(color);
            strokePaint.setColor(negativeColor(color));
            textPaint.setColor(negativeColor(color));
        }
        counter = counterData.getCounter();
        running = ((TimeCounterData) counterData).isRunning();
    }
}
