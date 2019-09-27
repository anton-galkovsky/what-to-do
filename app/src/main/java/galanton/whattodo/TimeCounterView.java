package galanton.whattodo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;

class TimeCounterView extends CounterView {

    private int color;
    private long counter;
    private boolean running;

    private Paint fillPaint;
    private Paint strokePaint;
    private Paint textPaint;

    TimeCounterView(int counterViewId, Bundle timeCounterDataExtras, int minViewSide, MainActivity activity) {
        super(counterViewId, minViewSide, activity);

        color = timeCounterDataExtras.getInt("color");
        counter = timeCounterDataExtras.getLong(CounterView.getCounterSource());
        running = timeCounterDataExtras.getBoolean("running");

        fillPaint = new Paint();
        fillPaint.setColor(color);
        fillPaint.setStyle(Paint.Style.FILL);
        strokePaint = new Paint();
        strokePaint.setColor(negativeColor(color));
        strokePaint.setStyle(Paint.Style.FILL);
        textPaint = new Paint();
        textPaint.setColor(negativeColor(color));
        textPaint.setTypeface(Typeface.create("monospace", Typeface.NORMAL));
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

        String text = timeFormat(counter / 1000);
        float textSize = Math.min(1.5f * getActualWidth() / text.length(), 70);
        textPaint.setTextSize(textSize);
        canvas.drawText(text, textSize / 6, getActualHeight() / 2.0f + textSize / 3, textPaint);
    }

    @Override
    int getScaledCounter() {
        return (int) (counter / 1000);
    }

    @Override
    void adjustParams(Bundle counterDataExtras) {
        int newColor = counterDataExtras.getInt("color");
        if (newColor != color) {
            color = newColor;
            fillPaint.setColor(color);
            strokePaint.setColor(negativeColor(color));
            textPaint.setColor(negativeColor(color));
        }
        counter = counterDataExtras.getLong(CounterView.getCounterSource());
        running = counterDataExtras.getBoolean("running");
    }

    private String timeFormat(long seconds) {
        if (seconds < 60) {
            return "" + seconds;
        }
        if (seconds < 20000) {   // or < 3600
            long secs = seconds % 60;
            return seconds / 60 + ":" + secs / 10 + "" + secs % 10;
        }
        long mins = seconds % 3600 / 60;
        long secs = seconds % 60;
        return seconds / 3600 + ":" + mins / 10 + "" + mins % 10 + ":" + secs / 10 + "" + secs % 10;
    }
}
