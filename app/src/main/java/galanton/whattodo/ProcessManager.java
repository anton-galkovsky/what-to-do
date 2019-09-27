package galanton.whattodo;

import android.os.Bundle;
import android.view.View;

class ProcessManager {

    private DataManager dataManager;
    private UserInterfaceManager userInterfaceManager;

    ProcessManager(int screenWidth, int screenHeight, String fileName, ScreenType screenType, MainActivity activity) {
        dataManager = new DataManager(fileName, activity);
        userInterfaceManager = new UserInterfaceManager(screenWidth, screenHeight,
                dataManager.getIdArr(), dataManager.getDataArr(), screenType, activity);
    }

    void addTimeCounter(int color) {
        int id = dataManager.addTimeCounterData(color);
        userInterfaceManager.addTimeCounterView(id, dataManager.getDataExtrasById(id));
    }

    void addClickCounter(int color) {
        int id = dataManager.addClickCounterData(color);
        userInterfaceManager.addClickCounterView(id, dataManager.getDataExtrasById(id));
    }

    void deleteCounter(View view) {
        int id = ((CounterView) view).getCounterViewId();
        dataManager.removeCounterData(id);
        userInterfaceManager.removeCounterView(id);
    }

    void adjustCounter(View view, int color, long counterInc) {
        int id = ((CounterView) view).getCounterViewId();
        dataManager.adjustCounterData(id, color, counterInc);
        userInterfaceManager.adjustCounterView(id, dataManager.getDataExtrasById(id));
    }

    void onClick(View view, long time) {
        int id = ((CounterView) view).getCounterViewId();
        dataManager.onCounterDataClick(id, time);
        userInterfaceManager.adjustCounterView(id, dataManager.getDataExtrasById(id));
    }

    void updateTimes(long time) {
        dataManager.updateTimes(time);
        userInterfaceManager.adjustCounterViews(dataManager.getIdArr(), dataManager.getDataArr());
    }

    void setScreenType(ScreenType screenType) {
        userInterfaceManager.setScreenType(screenType, dataManager.getIdArr(), dataManager.getDataArr());
    }

    Bundle getExtras(View view) {
        int id = ((CounterView) view).getCounterViewId();
        return dataManager.getDataExtrasById(id);
    }

    void onSync() {
        dataManager.onSync();
        userInterfaceManager.adjustCounterViews(dataManager.getIdArr(), dataManager.getDataArr());
    }
}
