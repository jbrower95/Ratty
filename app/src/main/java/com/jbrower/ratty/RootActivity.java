package com.jbrower.ratty;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jbrower.ratty.event.RattyMenuFailedEvent;
import com.jbrower.ratty.event.RattyMenuReceivedEvent;
import com.jbrower.ratty.model.RattyMenu;
import com.jbrower.ratty.network.Api;
import com.squareup.otto.Subscribe;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;

public class RootActivity extends AppCompatActivity {

    /* The current menu */
    private RattyMenu mMenu;
    private static final String KEY_MENU = RootActivity.class.getPackage() + ".menu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        App.getBus().register(this);

        ButterKnife.bind(this);

        if (savedInstanceState == null) {
        /* Spoof a request for this day */
            final Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, 9);
            calendar.set(Calendar.DAY_OF_MONTH, 29);
            calendar.set(Calendar.YEAR, 2015);
            calendar.set(Calendar.HOUR, 14);

            final Date spoofedDate = new Date(calendar.getTimeInMillis());
            Api.requestMenuForEateryAtTime(Api.Eatery.RATTY, spoofedDate);
        } else {
            mMenu = savedInstanceState.getParcelable(KEY_MENU);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        if (mMenu != null) {
            bundle.putParcelable(KEY_MENU, bundle);
        }
    }

    @Subscribe
    public void onReceiveRattyMenu(RattyMenuReceivedEvent event) {
        mMenu = event.getData();
        showMenu();
        Utils.showToast("Loaded menu!");
    }

    @Subscribe
    public void onFailedRattyMenu(RattyMenuFailedEvent event) {
        Utils.showToast(event.getData().getLocalizedMessage());
    }

    private void showMenu() {
        if (mMenu != null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, RattyFragment.create(mMenu))
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getBus().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
