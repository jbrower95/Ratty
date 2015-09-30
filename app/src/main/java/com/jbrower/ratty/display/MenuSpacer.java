package com.jbrower.ratty.display;

import android.view.View;

import com.jbrower.ratty.R;

/**
 * A small 2dp space.
 * Created by Justin on 9/29/15.
 */
public class MenuSpacer implements MenuItem {
    @Override
    public int getLayout() {
        return R.layout.menu_list_spacer;
    }

    @Override
    public void bindToView(View view) {
        // nothing to do here
    }
}
