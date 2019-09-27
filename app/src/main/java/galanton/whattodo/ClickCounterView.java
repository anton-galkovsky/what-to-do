package galanton.whattodo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;

class ClickCounterView extends CounterView {

    private int color;
    private long counter;

    private Paint fillPaint;
    private Paint textPaint;

    ClickCounterView(int counterViewId, Bundle clickCounterDataExtras, int minViewSide, MainActivity activity) {
        super(counterViewId, minViewSide, activity);

        color = clickCounterDataExtras.getInt("color");
        counter = clickCounterDataExtras.getLong("counter");

        fillPaint = new Paint();
        fillPaint.setColor(color);
        fillPaint.setStyle(Paint.Style.FILL);
        textPaint = new Paint();
        textPaint.setColor(negativeColor(color));
        textPaint.setTextSize(100);
        textPaint.setTypeface(Typeface.create("monospace", Typeface.NORMAL));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), fillPaint);

        String text = "" + counter;
        float textSize = Math.min(1.5f * getActualWidth() / text.length(), 100);
        textPaint.setTextSize(textSize);
        canvas.drawText(text, textSize / 6, getActualHeight() / 2.0f + textSize / 3, textPaint);
    }

    @Override
    int getScaledCounter() {
        return (int) (counter * 1000);
    }

    @Override
    void adjustParams(Bundle counterDataExtras) {
        int newColor = counterDataExtras.getInt("color");
        if (newColor != color) {
            color = newColor;
            fillPaint.setColor(color);
            textPaint.setColor(negativeColor(color));
        }
        counter = counterDataExtras.getLong(CounterView.getCounterSource());
    }
}
