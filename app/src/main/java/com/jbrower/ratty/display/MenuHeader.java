package com.jbrower.ratty.display;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.jbrower.ratty.R;

/**
 * A section of a dining hall. Just a generic header really.
 * Created by Justin on 9/29/15.
 */
public class MenuHeader implements MenuItem {

    private final String mTitle;

    public MenuHeader(@NonNull String title) {
        final StringBuilder titleBuilder = new StringBuilder();

        for (char c : title.toCharArray()) {
            titleBuilder.append(Character.toUpperCase(c));
        }

        mTitle = titleBuilder.toString();
    }

    @Override
    public int getLayout() {
        return R.layout.dining_section;
    }

    @Override
    public void bindToView(final View view) {
        final TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(mTitle);
        view.setAlpha(.5f);
    }
}
