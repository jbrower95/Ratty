package com.jbrower.ratty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jbrower.ratty.event.RattyMenuFailedEvent;
import com.jbrower.ratty.event.RattyMenuReceivedEvent;
import com.jbrower.ratty.model.RattyMenu;
import com.jbrower.ratty.network.Api;
import com.squareup.otto.Subscribe;

public class RootActivity extends AppCompatActivity {

    private static final String API_URL = "https://api.students.brown.edu/dining/menu";

    private RattyMenu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        App.getBus().register(this);
        System.out.println("Loading menu for ratty..");
        Api.requestCurrentMenuForEatery(Api.Eatery.RATTY);
    }

    @Subscribe
    public void onReceiveRattyMenu(RattyMenuReceivedEvent event) {
        mMenu = event.getData();
        Utils.showToast("Loaded menu!");
    }

    @Subscribe
    public void onFailedRattyMenu(RattyMenuFailedEvent event) {
        Utils.showToast("Couldn't load menu: " + event.getData().getLocalizedMessage());
    }

    @Override
    protected  void onDestroy() {
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
