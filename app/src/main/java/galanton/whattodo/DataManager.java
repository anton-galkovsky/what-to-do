package galanton.whattodo;

import android.content.Intent;

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

    CounterData getDataById(int id) {
        return counterDataArr.get(findIndexById(id));
    }

    void removeCounterData(int id) {
        int index = findIndexById(id);
        counterDataArr.remove(index);
        counterIdArr.remove(index);
        storageManager.writeDataArr(counterDataArr);
    }

    void adjustCounterData(int id, int color, long counter, boolean newColor) {
        int index = findIndexById(id);
        counterDataArr.get(index).adjustParams(color, counter, newColor);
        storageManager.writeDataArr(counterDataArr);
    }

    void onCounterDataClick(int id, long time) {
        int index = findIndexById(id);
        counterDataArr.get(index).onClick(time);
        storageManager.writeDataArr(counterDataArr);
    }

    void updateTimes(long time) {
        for (int i = 0; i < counterIdArr.size(); i++) {
            counterDataArr.get(i).updateTimes(time);
        }
    }

    void putExtras(Intent intent, int id) {
        int index = findIndexById(id);
        counterDataArr.get(index).putExtras(intent);
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
