package com.jbrower.ratty.display;

import android.view.View;

/**
 * An interface for things we can display in our menu.
 * Created by Justin on 9/29/15.
 */
public interface MenuItem {
    int getLayout();
    void bindToView(View view);
}
