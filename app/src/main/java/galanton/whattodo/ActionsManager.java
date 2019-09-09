package galanton.whattodo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

class ActionsManager {

    private ArrayList<CounterView> counterViews;
    private ArrayList<HorizontalContainer> containers;
    private MainActivity activity;

    private int screenWidth;
    private int usefulScreenWidth;
    private int minViewSide;
    private String fileName;

    ActionsManager(int screenWidth, int screenHeight, String fileName, MainActivity activity) {
        this.screenWidth = screenWidth;
        new HorizontalContainer(activity);  // initializing divider step
        usefulScreenWidth = screenWidth - 2 * HorizontalContainer.getDividerStep();
        this.activity = activity;
        this.fileName = fileName;
        minViewSide = Math.max(screenHeight, screenWidth) / 12;
        containers = new ArrayList<>();

        readActionsFromFile();
        updateScreen();
    }

    void addClickCounter(int color) {
        counterViews.add(new ClickCounterView(new ClickCounterData(color), minViewSide, activity));
        writeActionsToFile();
        updateScreen();
    }

    void addTimeCounter(int color) {
        counterViews.add(new TimeCounterView(new TimeCounterData(color), minViewSide, activity));
        writeActionsToFile();
        updateScreen();
    }

    void deleteCounter(View v) {
        counterViews.remove((CounterView) v);
        writeActionsToFile();
        updateScreen();
    }

    void adjustCounter(View v, int color, long counter, boolean newColor) {
        ((CounterView) v).setCounter(counter);
        if (newColor) {
            ((CounterView) v).setColor(color);
        }
        writeActionsToFile();
        updateScreen();
    }

    void onClick(View v) {
        for (CounterView counterView : counterViews) {
            if (counterView == v) {
                counterView.onClick();
                break;
            }
        }
        writeActionsToFile();
        updateScreen();
    }

    void updateTimes(long time) {
        for (CounterView counterView : counterViews) {
            counterView.updateTimes(time);
        }
        writeActionsToFile();
        updateScreen();
    }

    private void updateScreen() {
        Collections.sort(counterViews);

        for (LinearLayout container : containers) {
            container.removeAllViews();
        }
        LinearLayout layout = activity.findViewById(R.id.base_linear_layout);
        layout.removeAllViews();

        for (CounterView counterView : counterViews) {
            double square = minViewSide * minViewSide + counterView.getScaledCounter();
            if ((int) (square / minViewSide) <= usefulScreenWidth) {
                counterView.setWidth((int) (square / minViewSide));
                counterView.setHeight(minViewSide);
            } else {
                counterView.setWidth(usefulScreenWidth);
                counterView.setHeight((int) (square / usefulScreenWidth));
            }
            for (int i = 0; ; i++) {
                if (containers.size() == i) {
                    containers.add(new HorizontalContainer(activity));
                }
                if (containers.get(i).getActualWidthWith(counterView) <= screenWidth) {
                    containers.get(i).addMeasurableView(counterView);
                    break;
                }
            }
        }
        for (HorizontalContainer container : containers) {
            layout.addView(container);
        }
        layout.invalidate();
    }

    private void writeActionsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(activity.openFileOutput(fileName, Context.MODE_PRIVATE))) {
            Integer cvSize = counterViews.size();
            oos.writeObject(cvSize);
            for (CounterView counterView : counterViews) {
                oos.writeObject(counterView.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readActionsFromFile() {
        counterViews = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(activity.openFileInput(fileName))) {
            Integer cvSize = (Integer) ois.readObject();
            for (int i = 0; i < cvSize; i++) {
                Object obj = ois.readObject();
                if (obj instanceof TimeCounterData) {
                    counterViews.add(new TimeCounterView((TimeCounterData) obj, minViewSide, activity));
                } else if (obj instanceof ClickCounterData) {
                    counterViews.add(new ClickCounterView((ClickCounterData) obj, minViewSide, activity));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void putExtras(Intent intent, View v) {
        ((CounterView) v).putExtras(intent);
    }
}
