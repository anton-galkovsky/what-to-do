package galanton.whattodo;

import android.os.Bundle;

interface CounterData {

    void adjustParams(int color, long counterInc);
    void onClick(long time);
    void updateTimes(long time);
    void onSync();

    Bundle getExtras();
}
