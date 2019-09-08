package galanton.whattodo;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

class ActionsManager {

    private ArrayList<ClickCounterView> clickCounterViews;
    private ArrayList<TimeCounterView> timeCounterViews;
    private ArrayList<HorizontalContainer> containers;
    private MainActivity activity;

    private int screenWidth;
    private int usefulScreenWidth;
    private int minViewSide;
    private String fileName;

    ActionsManager(int screenWidth, int screenHeight, String fileName, MainActivity activity) {
        this.screenWidth = screenWidth;
        usefulScreenWidth = screenWidth - 2 * HorizontalContainer.getDividerStep();
        this.activity = activity;
        this.fileName = fileName;
        minViewSide = Math.max(screenHeight, screenWidth) / 12;
        containers = new ArrayList<>();

        readActionsFromFile();
        updateScreen();
    }

    void addClickCounter(int color) {
        clickCounterViews.add(new ClickCounterView(new ClickCounterData(color), minViewSide, activity));
        writeActionsToFile();
        updateScreen();
    }

    void addTimeCounter(int color) {
        timeCounterViews.add(new TimeCounterView(new TimeCounterData(color), minViewSide, activity));
        writeActionsToFile();
        updateScreen();
    }

    void onClick(View v) {
        for (TimeCounterView timeCounterView : timeCounterViews) {
            if (timeCounterView == v) {
                timeCounterView.onClick();
                break;
            }
        }
        for (ClickCounterView clickCounterView : clickCounterViews) {
            if (clickCounterView == v) {
                clickCounterView.onClick();
                break;
            }
        }
        updateScreen();
    }

    void updateTimes(long time) {
        for (TimeCounterView timeCounterView : timeCounterViews) {
            timeCounterView.updateTimes(time);
        }
        writeActionsToFile();
        updateScreen();
    }

    private void updateScreen() {
        Collections.sort(clickCounterViews);
        Collections.sort(timeCounterViews);

        for (LinearLayout container : containers) {
            container.removeAllViews();
        }
        LinearLayout layout = activity.findViewById(R.id.base_linear_layout);
        layout.removeAllViews();

        for (TimeCounterView timeCounterView : timeCounterViews) {
            double square = minViewSide * minViewSide + (int) (timeCounterView.getCounter() / 1000.0);
            if ((int) (square / minViewSide) <= usefulScreenWidth) {
                timeCounterView.setWidth((int) (square / minViewSide));
                timeCounterView.setHeight(minViewSide);
            } else {
                timeCounterView.setWidth(usefulScreenWidth);
                timeCounterView.setHeight((int) (square / usefulScreenWidth));
            }
            for (int i = 0; ; i++) {
                if (containers.size() == i) {
                    containers.add(new HorizontalContainer(activity));
                }
                if (containers.get(i).getActualWidthWith(timeCounterView) <= screenWidth) {
                    containers.get(i).addMeasurableView(timeCounterView);
                    break;
                }
            }
        }
        for (ClickCounterView clickCounterView : clickCounterViews) {
            double square = minViewSide * minViewSide + (int) (clickCounterView.getCounter() * 1000);
            if ((int) (square / minViewSide) <= usefulScreenWidth) {
                clickCounterView.setWidth((int) (square / minViewSide));
                clickCounterView.setHeight(minViewSide);
            } else {
                clickCounterView.setWidth(usefulScreenWidth);
                clickCounterView.setHeight((int) (square / usefulScreenWidth));
            }
            for (int i = 0; ; i++) {
                if (containers.size() == i) {
                    containers.add(new HorizontalContainer(activity));
                }
                if (containers.get(i).getActualWidthWith(clickCounterView) <= screenWidth) {
                    containers.get(i).addMeasurableView(clickCounterView);
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
            Integer tcsSize = timeCounterViews.size();
            Integer ccsSize = clickCounterViews.size();
            oos.writeObject(tcsSize);
            oos.writeObject(ccsSize);
            for (TimeCounterView timeCounterView : timeCounterViews) {
                oos.writeObject(timeCounterView.getData());
            }
            for (ClickCounterView clickCounterView : clickCounterViews) {
                oos.writeObject(clickCounterView.getData());
            }
//            oos.writeObject(timeCounterViews);
//            oos.writeObject(clickCounterViews);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readActionsFromFile() {
        timeCounterViews = new ArrayList<>();
        clickCounterViews = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(activity.openFileInput(fileName))) {
            int tcsSize = (Integer) ois.readObject();
            int ccsSize = (Integer) ois.readObject();
            for (int i = 0; i < tcsSize; i++) {
                timeCounterViews.add(new TimeCounterView((TimeCounterData) ois.readObject(), minViewSide, activity));
            }
            for (int i = 0; i < ccsSize; i++) {
                clickCounterViews.add(new ClickCounterView((ClickCounterData) ois.readObject(), minViewSide, activity));
            }
//            timeCounterViews = (ArrayList<TimeCounterView>) ois.readObject();
//            clickCounterViews = (ArrayList<ClickCounterView>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
