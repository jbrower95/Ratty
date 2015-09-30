package com.jbrower.ratty.network;

import java.util.Map;

/**
 * All of the different Brown Menu API routes. (WIP)
 */
public enum Route {
    UNSPECIFIED(null),
    ROOT(""),
    GET_MENU("/dining/menu");

    final String mEndPoint;

    Route(String endPoint) {
        mEndPoint = endPoint;
    }

    private String getEndpoint() {
        return mEndPoint;
    }

    public String getWithParameters(Map<String, String> parameters) {
        final StringBuilder url = new StringBuilder(mPrefix);
        url.append(getEndpoint());
        if (parameters.isEmpty()) {
            return url.toString();
        } else {
            url.append('?');
        }

        for (String key : parameters.keySet()) {
            url.append(key);
            url.append('=');
            url.append(parameters.get(key));
            url.append('&');
        }

        final String result = url.toString();
        // This string contains an extra '&'. Remove it from the end.

        return result.substring(0, result.length() - 1);
    }

    public String get() {
        return mPrefix + getEndpoint();
    }

    private static String mPrefix;

    public static void setApiPrefix(String prefix) {
        mPrefix = prefix;
    }
}
