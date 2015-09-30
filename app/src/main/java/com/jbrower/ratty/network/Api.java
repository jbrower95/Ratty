package com.jbrower.ratty.network;

import com.jbrower.ratty.consumer.RattyMenuConsumer;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Justin on 9/25/15.
 */
public final class Api {

    private static final String API_BASE = "https://api.students.brown.edu";

    private static final String API_EXTENSION_DINING_MENU = "/dining/menu";

    private static final String CLIENT_ID = "82474103-9fc9-4284-b582-ae72180728fa";

    /**
     * Requests the current menu for an eatery.
     * @param eatery The eatery whose menu you want.
     */
    public static void requestCurrentMenuForEatery(Eatery eatery) {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", CLIENT_ID);
        parameters.put("eatery", eatery.toString());

        new NetworkThread.Builder()
                .get(Route.GET_MENU.getWithParameters(parameters))
                .withRoute(Route.GET_MENU)
                .withConsumer(new RattyMenuConsumer())
                .build().start();
    }

    /**
     * Requests the menu for a given date.
     * @param eatery The eatery to get the menu for.
     * @param date The date from which the menu should be loaded.
     */
    public static void requestMenuForEateryAtTime(Eatery eatery, Date date) {
        final Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", CLIENT_ID);
        parameters.put("eatery", eatery.toString());

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        /* Customize the time of the menu */
        parameters.put("year", Integer.toString(calendar.get(Calendar.YEAR)));
        parameters.put("month", Integer.toString(calendar.get(Calendar.MONTH)));
        parameters.put("day", Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
        parameters.put("hour", Integer.toString(calendar.get(Calendar.HOUR)));
        parameters.put("minute", Integer.toString(calendar.get(Calendar.MINUTE)));

        new NetworkThread.Builder()
                .get(Route.GET_MENU.getWithParameters(parameters))
                .withRoute(Route.GET_MENU)
                .withConsumer(new RattyMenuConsumer())
                .build().start();
    }

    static {
        Route.setApiPrefix(API_BASE);
    }

    public enum Eatery {
        RATTY("ratty"),
        VDUB("vdub");
        private String mName;
        Eatery(String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return mName;
        }
    }

}
