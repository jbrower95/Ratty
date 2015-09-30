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
 * Some sample JSON from the server:

 {
 "menus": [
 {
 "bistro": [
 "meatloaf w/ mushroom sauce",
 "vegan brown rice",
 "edamame beans w/ tri-colored peppers",
 "italian vegetable saute",
 "stir-fried carrots w/ fresh herbs",
 "dessert: panettone bread pudding"
 ],
 "chef's corner": [
 "mushroom macaroni & cheese",
 "edamame beans w/ tri-colored peppers",
 "italian vegetable saute",
 "stir-fried carrots w/ fresh herbs",
 "white & whole wheat pasta",
 "vegan garden vegetable sauce",
 "meat sauce",
 "choice of assorted pizzas",
 "for your safety, it is the customer's obligation to inform the server about any food allergies."
 ],
 "daily sidebars": [
 "salad bar: greek salad bar",
 "soup & bread station: italian sausage soup w/ tortellini and vegetarian corn chowder / honey bran flaxseed bread"
 ],
 "day": 29,
 "eatery": "ratty",
 "end_hour": 19,
 "end_minute": 30,
 "grill": [
 "hamburgers, cheeseburgers",
 "hot dogs",
 "grilled citrus herb chicken",
 "waffle cut fries",
 "sliced turkey & ham",
 "sliced american & cheddar cheese",
 "gingered turkey salad",
 "sliced lettuce and tomato",
 "accompaniments: cheese sauce,bbq chips"
 ],
 "meal": "dinner",
 "mMonth": 9,
 "roots & shoots": [
 "eggplant parmesan",
 "vegan baked polenta",
 "edamame beans w/ tri-colored peppers",
 "stir-fried carrots w/ fresh herbs",
 "southwest black bean & sweet potato vegan patty",
 "traditional vegan patty",
 "vegan brown rice",
 "carrot sticks",
 "tri-colored pasta salad",
 "red wheatberry grain bar",
 "for your safety, it is the customer's obligation to inform the server about any food allergies."
 ],
 "start_hour": 16,
 "start_minute": 0,
 "year": 2015
 }
 ],
 "num_results": 1
 }




 *
 *
 * Created by Justin on 9/29/15.
 */
public final class RattyMenu {

    private static final String KEY_MENUS = "menus";

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

    public RattyMenu(String response) throws JSONException {
        final JSONObject root = new JSONObject(response);
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
            default:
                return new LinkedList<>();
        }
    }
}
