package galanton.whattodo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

class TimeCounterView extends CounterView implements Comparable {

    // Counter part
    private TimeCounterData timeCounterData;

    // View part
    private Paint fillPaint;
    private Paint strokePaint;
    private Paint textPaint;

    TimeCounterView(TimeCounterData timeCounterData, int minViewSide, MainActivity activity) {
        super(minViewSide, activity);

        this.timeCounterData = timeCounterData;                     // or copy constructor???

        fillPaint = new Paint();
        fillPaint.setColor(timeCounterData.getColor());
        fillPaint.setStyle(Paint.Style.FILL);
        strokePaint = new Paint();
        strokePaint.setColor(negativeColor(timeCounterData.getColor()));
        strokePaint.setStyle(Paint.Style.FILL);
        textPaint = new Paint();
        textPaint.setColor(negativeColor(timeCounterData.getColor()));
        textPaint.setTextSize(70);

        setOnClickListener(activity);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), fillPaint);
        if (timeCounterData.isRunning()) {
            int step = 20;
            for (int i = 0; i - step < getWidth(); i += step) {
                for (int j = 0; j - step < getHeight(); j += step) {
                    canvas.drawCircle(i, j, 3, strokePaint);
                }
            }
        }
        canvas.drawText("" + timeCounterData.getCounter() / 1000, 0, 70, textPaint);
    }

    @Override
    int getScaledCounter() {
        return (int) (timeCounterData.getCounter() / 1000);
    }

    @Override
    TimeCounterData getData() {
        return timeCounterData;
    }

    @Override
    void onClick() {
        timeCounterData.changeRunning();
    }

    @Override
    void updateTimes(long time) {
        timeCounterData.updateTimes(time);
    }
}
