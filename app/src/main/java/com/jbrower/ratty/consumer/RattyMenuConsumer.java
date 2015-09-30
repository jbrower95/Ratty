package com.jbrower.ratty.consumer;

import com.jbrower.ratty.App;
import com.jbrower.ratty.event.RattyMenuFailedEvent;
import com.jbrower.ratty.event.RattyMenuReceivedEvent;
import com.jbrower.ratty.model.RattyMenu;

/**
 * A consumer for the Ratty's menu data.
 * Created by Justin on 9/29/15.
 */
public class RattyMenuConsumer implements Consumer {
    @Override
    public void consume(String response, int responseCode) {
        try {
            final RattyMenu menu = new RattyMenu(response);
            App.getBus().post(new RattyMenuReceivedEvent(menu));
        } catch (Exception e) {
            App.getBus().post(new RattyMenuFailedEvent(e));
        }
    }

    @Override
    public void failed(Exception e) {
        App.getBus().post(new RattyMenuFailedEvent(e));
    }

    @Override
    public void failed(int statusCode) {
        App.getBus().post(new RattyMenuFailedEvent("Couldn't connect to server (" + statusCode + ")"));
    }
}
