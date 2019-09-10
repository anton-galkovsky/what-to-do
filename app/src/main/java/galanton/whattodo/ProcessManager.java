package galanton.whattodo;

import android.content.Intent;
import android.view.View;

class ProcessManager {

    private DataManager dataManager;
    private UserInterfaceManager userInterfaceManager;

    ProcessManager(int screenWidth, int screenHeight, String fileName, MainActivity activity) {
        dataManager = new DataManager(fileName, activity);
        userInterfaceManager = new UserInterfaceManager(
                screenWidth, screenHeight, dataManager.getIdArr(), dataManager.getDataArr(), activity);
    }

    void addTimeCounter(int color) {
        int id = dataManager.addTimeCounterData(color);
        userInterfaceManager.addTimeCounterView(id, (TimeCounterData) dataManager.getDataById(id));
    }

    void addClickCounter(int color) {
        int id = dataManager.addClickCounterData(color);
        userInterfaceManager.addClickCounterView(id, (ClickCounterData) dataManager.getDataById(id));
    }

    void deleteCounter(View view) {
        int id = ((CounterView) view).getCounterViewId();
        dataManager.removeCounterData(id);
        userInterfaceManager.removeCounterView(id);
    }

    void adjustCounter(View view, int color, long counter, boolean newColor) {
        int id = ((CounterView) view).getCounterViewId();
        dataManager.adjustCounterData(id, color, counter, newColor);
        userInterfaceManager.adjustCounterView(id, dataManager.getDataById(id));
    }

    void onClick(View view, long time) {
        int id = ((CounterView) view).getCounterViewId();
        dataManager.onCounterDataClick(id, time);
        userInterfaceManager.adjustCounterView(id, dataManager.getDataById(id));
    }

    void updateTimes(long time) {
        dataManager.updateTimes(time);
        userInterfaceManager.adjustCounterViews(dataManager.getIdArr(), dataManager.getDataArr());
    }

    void putExtras(Intent intent, View view) {
        int id = ((CounterView) view).getCounterViewId();
        dataManager.putExtras(intent, id);
    }
}
