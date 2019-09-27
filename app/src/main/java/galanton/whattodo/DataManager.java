package galanton.whattodo;

import android.os.Bundle;

import java.util.ArrayList;

class DataManager {

    private ArrayList<CounterData> counterDataArr;
    private ArrayList<Integer> counterIdArr;
    private int newId;

    private StorageManager storageManager;

    DataManager(String fileName, MainActivity activity) {
        storageManager = new StorageManager(fileName, activity);
        counterDataArr = storageManager.readDataArr();
        counterIdArr = new ArrayList<>();
        for (newId = 0; newId < counterDataArr.size(); newId++) {
            counterIdArr.add(newId);
        }
    }

    ArrayList<CounterData> getDataArr() {
        return counterDataArr;
    }

    ArrayList<Integer> getIdArr() {
        return counterIdArr;
    }

    int addTimeCounterData(int color) {
        counterDataArr.add(new TimeCounterData(color));
        counterIdArr.add(newId);
        storageManager.writeDataArr(counterDataArr);
        return newId++;
    }

    int addClickCounterData(int color) {
        counterDataArr.add(new ClickCounterData(color));
        counterIdArr.add(newId);
        storageManager.writeDataArr(counterDataArr);
        return newId++;
    }

    Bundle getDataExtrasById(int id) {
        int index = findIndexById(id);
        return counterDataArr.get(index).getExtras();
    }

    void removeCounterData(int id) {
        int index = findIndexById(id);
        counterDataArr.remove(index);
        counterIdArr.remove(index);
        storageManager.writeDataArr(counterDataArr);
    }

    void adjustCounterData(int id, int color, long counterInc) {
        int index = findIndexById(id);
        counterDataArr.get(index).adjustParams(color, counterInc);
        storageManager.writeDataArr(counterDataArr);
    }

    void onCounterDataClick(int id, long time) {
        int index = findIndexById(id);
        counterDataArr.get(index).onClick(time);
        storageManager.writeDataArr(counterDataArr);
    }

    void updateTimes(long time) {
        for (CounterData counterData : counterDataArr) {
            counterData.updateTimes(time);
        }
    }

    void onSync() {
        for (CounterData counterData : counterDataArr) {
            counterData.onSync();
        }
    }

    private int findIndexById(int id) {
        for (int i = 0; i < counterIdArr.size(); i++) {
            if (counterIdArr.get(i) == id) {
                return i;
            }
        }
        return -1;
    }
}
