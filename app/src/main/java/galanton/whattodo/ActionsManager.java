package galanton.whattodo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

class ActionsManager {

    private ArrayList<ActionView> actionViews;
    private ArrayList<HorizontalContainer> containers;
    private Activity activity;
    private int screenWidth;

    private int minViewSide;
    private String fileName;

    ActionsManager(int screenWidth, int screenHeight, String fileName, Activity activity) {
        this.screenWidth = screenWidth;
        minViewSide = Math.max(screenHeight, screenWidth) / 12;
        this.activity = activity;
        this.fileName = fileName;
        readActionsFromFile();
        containers = new ArrayList<>();
        updateScreen();
    }

    void addAction(int color) {
        actionViews.add(new ActionView(new Action(color), activity, minViewSide));
        writeActionsToFile();
        updateScreen();
    }

    void updateTimes(long time) {
        for (ActionView actionView : actionViews) {
            actionView.updateTimes(time);
        }
        writeActionsToFile();
        updateScreen();
    }

    private void updateScreen() {
        Collections.sort(actionViews);

        for (LinearLayout container : containers) {
            container.removeAllViews();
        }
        LinearLayout layout = activity.findViewById(R.id.base_linear_layout);
        layout.removeAllViews();

        for (ActionView actionView : actionViews) {
//            double square = minViewSide * minViewSide + (int) Math.sqrt(actionView.getAllTime() / 1000.0);
            double square = minViewSide * minViewSide + (int) (actionView.getAllTime() / 1000.0);
            if (square / minViewSide <= screenWidth) {
                actionView.setWidth((int) (square / minViewSide));
                actionView.setHeight(minViewSide);
            } else {
                actionView.setWidth(screenWidth);
                actionView.setHeight((int) (square / minViewSide));
            }
            for (int i = 0;; i++) {
                if (containers.size() == i) {
                    containers.add(new HorizontalContainer(activity));
                }
                if (containers.get(i).getActualWidthWith(actionView) <= screenWidth) {
                    containers.get(i).addActionView(actionView);
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
        ArrayList<Action> actions = new ArrayList<>();
        for (ActionView actionView : actionViews) {
            actions.add(new Action(actionView));
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(activity.openFileOutput(fileName, Context.MODE_PRIVATE))) {
            oos.writeObject(actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readActionsFromFile() {
        ArrayList<Action> actions = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(activity.openFileInput(fileName))) {
            actions = (ArrayList<Action>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        actionViews = new ArrayList<>();
        for (Action action : actions) {
            actionViews.add(new ActionView(action, activity, minViewSide));
        }
    }
}
