package galanton.whattodo;

import android.content.Context;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class StorageManager {

    private MainActivity activity;
    private String fileName;

    StorageManager(String fileName, MainActivity activity) {
        this.fileName = fileName;
        this.activity = activity;
    }

    ArrayList<CounterData> readDataArr() {
        ArrayList<CounterData> counterDataArr = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(activity.openFileInput(fileName))) {
            counterDataArr = (ArrayList<CounterData>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counterDataArr;
    }

    void writeDataArr(ArrayList<CounterData> counterDataArr) {
        try (ObjectOutputStream oos = new ObjectOutputStream(activity.openFileOutput(fileName, Context.MODE_PRIVATE))) {
            oos.writeObject(counterDataArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
