package galanton.whattodo;

import android.widget.LinearLayout;

import java.util.ArrayList;

class UserInterfaceManager {

    private ArrayList<CounterView> counterViews;
    private ArrayList<HorizontalContainer> containers;

    private MainActivity activity;

    private int screenWidth;
    private int usefulScreenWidth;
    private int minViewSide;

    private boolean needRegroup;

    UserInterfaceManager(int screenWidth, int screenHeight,
                         ArrayList<Integer> counterIdArr, ArrayList<CounterData> counterDataArr, MainActivity activity) {
        this.activity = activity;
        this.screenWidth = screenWidth;
        usefulScreenWidth = screenWidth - 2 * new HorizontalContainer(activity).getDividerStep();
        minViewSide = Math.max(screenHeight, screenWidth) / 12;
        containers = new ArrayList<>();
        counterViews = new ArrayList<>();

        for (int i = 0; i < counterDataArr.size(); i++) {
            CounterData data = counterDataArr.get(i);
            int id = counterIdArr.get(i);
            if (data instanceof TimeCounterData) {
                counterViews.add(new TimeCounterView(id, (TimeCounterData) data, minViewSide, activity));
            } else if (data instanceof ClickCounterData) {
                counterViews.add(new ClickCounterView(id, (ClickCounterData) data, minViewSide, activity));
            }
        }

        needRegroup = true;
        updateScreen();
    }

    void addTimeCounterView(int id, TimeCounterData timeCounterData) {
        counterViews.add(new TimeCounterView(id, timeCounterData, minViewSide, activity));
        needRegroup = true;
        updateScreen();
    }

    void addClickCounterView(int id, ClickCounterData clickCounterData) {
        counterViews.add(new ClickCounterView(id, clickCounterData, minViewSide, activity));
        needRegroup = true;
        updateScreen();
    }

    void removeCounterView(int id) {
        counterViews.remove(findViewById(id));
        needRegroup = true;
        updateScreen();
    }

    void adjustCounterView(int id, CounterData counterData) {
        findViewById(id).adjustParams(counterData);
        needRegroup = true;
        updateScreen();
    }

    void adjustCounterViews(ArrayList<Integer> idArr, ArrayList<CounterData> dataArr) {
        for (int i = 0; i < idArr.size(); i++) {
            findViewById(idArr.get(i)).adjustParams(dataArr.get(i));
        }
        // without regroup
        updateScreen();
    }

    private void updateScreen() {
        sortViews();

        for (CounterView counterView : counterViews) {
            double square = Math.max(minViewSide * minViewSide, counterView.getScaledCounter());
            if ((int) (square / minViewSide) <= usefulScreenWidth) {
                counterView.setWidth((int) (square / minViewSide));
                counterView.setHeight(minViewSide);
            } else {
                counterView.setWidth(usefulScreenWidth);
                counterView.setHeight((int) (square / usefulScreenWidth));
            }
        }

        for (HorizontalContainer container : containers) {
            if (container.getActualWidth() > screenWidth) {
                needRegroup = true;
                break;
            }
        }

        if (needRegroup) {
            regroupViews();
        }

        activity.findViewById(R.id.base_linear_layout).invalidate();
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
