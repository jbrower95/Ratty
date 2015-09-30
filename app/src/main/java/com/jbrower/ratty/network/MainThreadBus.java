package com.jbrower.ratty.network;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * A bus that allows message posting from any thread.
 * http://stackoverflow.com/questions/26692240/thread-safety-background-thread-and-cache-avoid-race-conditions
 */
public class MainThreadBus
{
    private final Bus mBus;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public MainThreadBus(final Bus bus) {
        if (bus == null)
            throw new NullPointerException("ERROR: bus == null");
        mBus = bus;
    }

    public void register(Object obj) {
        mBus.register(obj);
    }

    public void unregister(Object obj) {
        mBus.unregister(obj);
    }

    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mBus.post(event);
        }
        else {
            mHandler.post(new Runnable() {
                @Override
                public void run()
                {
                    mBus.post(event);
                }
            });
        }
    }
}