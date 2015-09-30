package com.jbrower.ratty;

import android.content.Context;
import android.widget.Toast;

/**
 * Some utils for displaying events.
 * Created by Justin on 9/3/15.
 */
public final class Utils {

    /**
     * Shows a toast with the specified text.
     * @param text The text to show.
     */
    public static void showToast(final String text) {
        final Context context = App.getAppContext();
        final int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
