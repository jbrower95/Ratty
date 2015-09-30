package com.jbrower.ratty.display;

import android.view.View;
import android.widget.TextView;

import com.jbrower.ratty.R;


/**
 * An item of food available at a dining hall.
 * Created by Justin on 9/29/15.
 */
public class MenuFoodItem implements MenuItem {

    private final String mItem;

    public MenuFoodItem(String item) {
        mItem = item;
    }

    @Override
    public int getLayout() {
        return R.layout.menu_list_food_item;
    }

    @Override
    public void bindToView(View view) {
        TextView item = (TextView) view.findViewById(R.id.title);
        item.setText(mItem);
    }
}
