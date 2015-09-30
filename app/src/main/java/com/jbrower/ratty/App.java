package com.jbrower.ratty;

/**
 * A custom App class.
 */

import android.app.Application;
import android.content.Context;

import com.jbrower.ratty.network.MainThreadBus;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class App extends Application {

    /**
     * This is the bus that everyone should post to and read from. This will be alive as long as the app is alive.
     */
    private static final MainThreadBus apiBus = new MainThreadBus(new Bus(ThreadEnforcer.ANY));

    /**
     * A shared context reference for the app, so that things don't get messy.
     */
    private static Context mContextRef;

    /**
     * This is a thread safe bus to post messages to.
     * @return The shared app bus.
     */
    public static MainThreadBus getBus() {
        return apiBus;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContextRef = getApplicationContext();
    }

    public static String getDefinedString(int id) {
        return getAppContext().getString(id);
    }

    /**
     * Returns the static application context.
     * @return The static app context.
     */
    public static Context getAppContext() {
        return mContextRef;
    }
}
