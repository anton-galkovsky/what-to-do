package galanton.whattodo;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

class UserInterfaceManager {

    private ArrayList<CounterView> counterViews;
    private ArrayList<HorizontalContainer> containers;

    private MainActivity activity;

    private int screenWidth;
    private int usefulScreenWidth;
    private int minViewSide;

    private ScreenType screenType;

    private boolean needRegroup;

    UserInterfaceManager(int screenWidth, int screenHeight,
                         ArrayList<Integer> counterIdArr, ArrayList<CounterData> counterDataArr,
                         ScreenType screenType, MainActivity activity) {
        this.activity = activity;
        this.screenWidth = screenWidth;
        usefulScreenWidth = screenWidth - 2 * new HorizontalContainer(activity).getDividerStep();
        minViewSide = Math.max(screenHeight, screenWidth) / 12;
        containers = new ArrayList<>();
        counterViews = new ArrayList<>();
        this.screenType = screenType;
        CounterView.setCounterSource(screenType.value);

        for (int i = 0; i < counterDataArr.size(); i++) {
            CounterData data = counterDataArr.get(i);
            int id = counterIdArr.get(i);
            if (data instanceof TimeCounterData) {
                counterViews.add(new TimeCounterView(id, data.getExtras(), minViewSide, activity));
            } else if (data instanceof ClickCounterData) {
                counterViews.add(new ClickCounterView(id, data.getExtras(), minViewSide, activity));
            }
        }

        needRegroup = true;
        updateScreen();
    }

    void addTimeCounterView(int id, Bundle timeCounterDataExtras) {
        counterViews.add(new TimeCounterView(id, timeCounterDataExtras, minViewSide, activity));
        needRegroup = true;
        updateScreen();
    }

    void addClickCounterView(int id, Bundle clickCounterDataExtras) {
        counterViews.add(new ClickCounterView(id, clickCounterDataExtras, minViewSide, activity));
        needRegroup = true;
        updateScreen();
    }

    void removeCounterView(int id) {
        counterViews.remove(findViewById(id));
        needRegroup = true;
        updateScreen();
    }

    void adjustCounterView(int id, Bundle counterDataExtras) {
        findViewById(id).adjustParams(counterDataExtras);
        needRegroup = true;
        updateScreen();
    }

    void adjustCounterViews(ArrayList<Integer> idArr, ArrayList<CounterData> dataArr) {
        for (int i = 0; i < idArr.size(); i++) {
            findViewById(idArr.get(i)).adjustParams(dataArr.get(i).getExtras());
        }
        // without regroup
        updateScreen();
    }

    void setScreenType(ScreenType screenType, ArrayList<Integer> idArr, ArrayList<CounterData> dataArr) {
        CounterView.setCounterSource(screenType.value);
        this.screenType = screenType;

        needRegroup = true;
        adjustCounterViews(idArr, dataArr);
    }

    private void updateScreen() {
        sortViews();

        for (CounterView counterView : counterViews) {
            int value = counterView.getScaledCounter();
            int bound = minViewSide * minViewSide;
            double square;
            if (screenType == ScreenType.ALL_TIME) {
                square = value < 2 * bound ? bound + value / 2.0 : value;
            } else {
                square = value < bound / 4.0 ? bound + value * 3 : 7 * value;
            }
            int newWidth, newHeight;
            if ((int) (square / minViewSide) <= usefulScreenWidth) {
                newWidth = (int) (square / minViewSide);
                newHeight = minViewSide;
            } else {
                newWidth = usefulScreenWidth;
                newHeight = (int) (square / usefulScreenWidth);
            }
            counterView.setWidth(newWidth);
            counterView.setHeight(newHeight);
        }

        for (HorizontalContainer container : containers) {
            if (container.getActualWidth() > screenWidth) {
                needRegroup = true;
                break;
            }
        }

        if (needRegroup) {
            needRegroup = false;
            regroupViews();
            activity.findViewById(R.id.base_linear_layout).invalidate();
        }
    }

    private void regroupViews() {
        for (LinearLayout container : containers) {
            container.removeAllViews();
        }
        LinearLayout layout = activity.findViewById(R.id.base_linear_layout);
        layout.removeAllViews();

        for (CounterView counterView : counterViews) {
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
    }

    private CounterView findViewById(int id) {
        for (CounterView counterView : counterViews) {
            if (counterView.getCounterViewId() == id) {
                return counterView;
            }
        }
        return null;
    }

    private void sortViews() {
        for (int i = 0; i + 1 < counterViews.size(); i++) {
            if (counterViews.get(i).getScaledCounter() < counterViews.get(i + 1).getScaledCounter()) {
                needRegroup = true;
                CounterView cv = counterViews.get(i);
                counterViews.set(i, counterViews.get(i + 1));
                counterViews.set(i + 1, cv);
                if (i > 0) {
                    i -= 2;
                }
            }
        }
    }
}
