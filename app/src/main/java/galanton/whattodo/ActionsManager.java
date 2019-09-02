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
    private Activity activity;
    private int screenWidth;
    private int screenHeight;

    private int minViewWidth;
    private int minViewHeight;
    private String fileName;

    ActionsManager(int screenWidth, int screenHeight, String fileName, Activity activity) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        minViewHeight = screenHeight / 13;
        this.activity = activity;
        this.fileName = fileName;
        readActionsFromFile();
        updateScreen();
    }

    void addAction(int color) {
        actionViews.add(new ActionView(new Action(color), activity));
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

        LinearLayout layout = activity.findViewById(R.id.base_linear_layout);
        layout.removeAllViews();

        long sumWeight = Math.max(1, sumWeight());
        for (ActionView actionView : actionViews) {
            actionView.setHeight(minViewHeight + (int) Math.max(actionView.getAllTime() / 1000, 0));
            layout.addView(actionView);
        }
    }

    private long sumWeight() {
        long sumWeight = 0;
        for (ActionView actionView : actionViews) {
            sumWeight += actionView.getAllTime();
        }
        return sumWeight;
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
            actionViews.add(new ActionView(action, activity));
        }
    }
}
