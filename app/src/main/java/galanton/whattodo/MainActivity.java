package galanton.whattodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, View.OnLongClickListener {

    private ProcessManager processManager;
    private View processedView;
    private ScreenType screenType;

    private final int callbackInterval = 1000;
    private Handler callbackHandler;
    private Runnable periodicCallback = new Runnable() {
        @Override
        public void run() {
            try {
                processManager.updateTimes(System.currentTimeMillis());
            } finally {
                callbackHandler.postDelayed(periodicCallback, callbackInterval);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPreferences();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        if (screenType == ScreenType.ALL_TIME) {
            navigationView.setCheckedItem(R.id.nav_all_time);
        } else {
            navigationView.setCheckedItem(R.id.nav_day);
        }

        processedView = null;
        callbackHandler = new Handler();
        processManager = new ProcessManager(dm.widthPixels, dm.heightPixels,
                "dataFile", screenType, this);
    }

    private void loadPreferences() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        screenType = ScreenType.valueOf(pref.getString(ScreenType.class.getName(), ScreenType.ALL_TIME.name()));
    }

    private void savePreferences() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString(ScreenType.class.getName(), screenType.name());
        ed.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        periodicCallback.run();
    }

    @Override
    protected void onStop() {
        super.onStop();
        callbackHandler.removeCallbacks(periodicCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (screenType == ScreenType.ALL_TIME) {
            menu.findItem(R.id.action_sync).setVisible(false);
        } else if (screenType == ScreenType.DAY) {
            menu.findItem(R.id.action_sync).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            startActivityForResult(new Intent(this, NewActionParamsActivity.class), 123);
        } else if (id == R.id.action_sync) {
            processManager.onSync();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onLongClick(View view) {
        if (screenType == ScreenType.ALL_TIME) {
            Intent intent = new Intent(this, AdjustActionParamsActivity.class);
            intent.putExtras(processManager.getExtras(view));
            processedView = view;
            startActivityForResult(intent, 234);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123) {
            if (resultCode == 1) {
                String type = data.getStringExtra("type");
                int color = data.getIntExtra("color", 0);
                if (type.equals("time")) {
                    processManager.addTimeCounter(color);
                } else if (type.equals("click")) {
                    processManager.addClickCounter(color);
                }
            }
        } else if (requestCode == 234) {
            if (resultCode == 1) {
                processManager.deleteCounter(processedView);
            } else if (resultCode == 2) {
                int color = data.getIntExtra("color", 0);
                long counterInc = data.getLongExtra("counter_inc", 0);
                processManager.adjustCounter(processedView, color, counterInc);
            }
            processedView = null;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_all_time) {
            screenType = ScreenType.ALL_TIME;
        } else if (id == R.id.nav_day) {
            screenType = ScreenType.DAY;
        }
        processManager.setScreenType(screenType);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        invalidateOptionsMenu();
        savePreferences();
        return true;
    }

    @Override
    public void onClick(View view) {
        processManager.onClick(view, System.currentTimeMillis());
    }
}
