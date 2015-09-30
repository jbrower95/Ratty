package com.jbrower.ratty.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * A local representation of the ratty menu.
 *
 *
 * Created by Justin on 9/29/15.
 */
public final class RattyMenu {

    private static final String KEY_MENUS = "menus";
    private static final String KEY_ERROR = "error";

    private static final String KEY_MENU_BISTRO = "bistro";
    private static final String KEY_MENU_CHEFS_CORNER = "chef's corner";
    private static final String KEY_MENU_DAILY_SIDEBARS = "daily sidebars";
    private static final String KEY_MENU_ROOTS_AND_SHOOTS = "roots & shoots";
    private static final String KEY_MENU_GRILL = "grill";
    private static final String KEY_NAME = "eatery";
    private static final String KEY_MEAL = "meal";
    private static final String KEY_MONTH = "month";
    private static final String KEY_DAY = "day";

    private final String mMeal;
    private final int mMonth;
    private final int mDay;
    private final String mName;
    private final List<String> menuItemsBistro;
    private final List<String> menuItemsChefsCorner;
    private final List<String> menuItemsDailySidebars;
    private final List<String> menuItemsRootsAndShoots;
    private final List<String> menuItemsGrill;

    public RattyMenu(String response) throws JSONException, RattyMenuException {
        final JSONObject root = new JSONObject(response);

        if (root.has(KEY_ERROR)) {
            final String error = root.getJSONArray(KEY_ERROR).getString(0);
            throw new RattyMenuException(error);
        }

        if (!root.has(KEY_MENUS)) {
            throw new JSONException("Menu object didn't contain a menu.");
        }

        final JSONArray menus = root.getJSONArray(KEY_MENUS);
        final JSONObject menu = menus.getJSONObject(0);

        menuItemsBistro = new LinkedList<>();
        final JSONArray bistro = menu.getJSONArray(KEY_MENU_BISTRO);
        if (bistro != null) {
            for (int i = 0; i < bistro.length(); i++) {
                menuItemsBistro.add(bistro.getString(i));
            }
        }

        menuItemsChefsCorner = new LinkedList<>();
        final JSONArray chefsCorner = menu.getJSONArray(KEY_MENU_CHEFS_CORNER);
        if (chefsCorner != null) {
            for (int i = 0; i < chefsCorner.length(); i++) {
                menuItemsChefsCorner.add(chefsCorner.getString(i));
            }
        }

        menuItemsDailySidebars = new LinkedList<>();
        final JSONArray dailySidebars = menu.getJSONArray(KEY_MENU_DAILY_SIDEBARS);
        if (dailySidebars != null) {
            for (int i = 0; i < dailySidebars.length(); i++) {
                menuItemsDailySidebars.add(dailySidebars.getString(i));
            }
        }

        menuItemsRootsAndShoots = new LinkedList<>();
        final JSONArray rootsAndShoots = menu.getJSONArray(KEY_MENU_ROOTS_AND_SHOOTS);
        if (rootsAndShoots != null) {
            for (int i = 0; i < rootsAndShoots.length(); i++) {
                menuItemsRootsAndShoots.add(rootsAndShoots.getString(i));
            }
        }

        menuItemsGrill = new LinkedList<>();
        final JSONArray grill = menu.getJSONArray(KEY_MENU_GRILL);
        if (grill != null) {
            for (int i = 0; i < grill.length(); i++) {
                menuItemsGrill.add(grill.getString(i));
            }
        }

        mMeal = menu.getString(KEY_MEAL);
        mMonth = menu.getInt(KEY_MONTH);
        mDay = menu.getInt(KEY_DAY);
        mName = menu.getString(KEY_NAME);
    }

    public String getMeal() {
        return mMeal;
    }

    public int getMonth() {
        return mMonth;
    }

    public String getEateryName() {
        return mName;
    }

    public List<String> getMenu(final String submenu) {
        switch (submenu) {
            case KEY_MENU_BISTRO:
                return menuItemsBistro;
            case KEY_MENU_CHEFS_CORNER:
                return menuItemsChefsCorner;
            case KEY_MENU_DAILY_SIDEBARS:
                return menuItemsDailySidebars;
            case KEY_MENU_ROOTS_AND_SHOOTS:
                return menuItemsRootsAndShoots;
            case KEY_MENU_GRILL:
                return menuItemsGrill;
            default:
                return new LinkedList<>();
        }
    }
}
