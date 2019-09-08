package galanton.whattodo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

public class ActionView extends android.support.v7.widget.AppCompatButton implements Comparable {

    // Action part
    private long allTime;
    private long lastUpdateTime;
    private int color;
    private boolean running;

    // View part
    private Paint fillPaint;
    private Paint strokePaint;
    private Paint textPaint;
    private int actualWidth;
    private int actualHeight;

    ActionView(Action action, Context context, int minViewSide) {
        super(context);
        allTime = action.getAllTime();
        lastUpdateTime = action.getLastUpdateTime();
        color = action.getColor();
        running = action.isRunning();

        fillPaint = new Paint();
        fillPaint.setColor(color);
        fillPaint.setStyle(Paint.Style.FILL);
        strokePaint = new Paint();
        strokePaint.setColor(negativeColor(color));
        strokePaint.setStyle(Paint.Style.FILL);
        textPaint = new Paint();
        textPaint.setColor(negativeColor(color));
        textPaint.setTextSize(100);

        actualWidth = 0;
        actualHeight = 0;
        setMinWidth(minViewSide);
        setMinimumWidth(minViewSide);
        setMinHeight(minViewSide);
        setMinimumHeight(minViewSide);

        setOnClickListener(v -> ((ActionView) v).changeRunning());
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
        canvas.drawText("" + allTime / 1000, 0, 100, textPaint);
    }

    public long getAllTime() {
        return allTime;
    }

    public void setAllTime(long allTime) {
        this.allTime = allTime;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void setWidth(int pixels) {
        super.setWidth(pixels);
        actualWidth = pixels;
    }

    @Override
    public void setHeight(int pixels) {
        super.setHeight(pixels);
        actualHeight = pixels;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return (int) (((ActionView) o).allTime - allTime);
    }

    long getLastUpdateTime() {
        return lastUpdateTime;
    }

    void updateTimes(long time) {
        if (running) {
            allTime += time - lastUpdateTime;
            lastUpdateTime = time;
        }
    }

    int getActualWidth() {
        return actualWidth;
    }

    private int negativeColor(int color) {
        return Color.rgb(255 - Color.red(color),
                255 - Color.green(color),
                255 - Color.blue(color));
    }

    private void changeRunning() {
        long time = System.currentTimeMillis();
        if (running) {
            allTime += time - lastUpdateTime;
        }
        lastUpdateTime = time;
        running = !running;
    }

    public int getActualHeight() {
        return actualHeight;
    }
}
