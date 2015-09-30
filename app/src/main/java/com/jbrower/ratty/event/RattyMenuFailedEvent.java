package com.jbrower.ratty.event;

/**
 * Signifies that the ratty menu couldn't be loaded.
 * Created by Justin on 9/29/15.
 */
public class RattyMenuFailedEvent extends BaseApiFailureEvent {

    public RattyMenuFailedEvent(Exception e) {
        super(e);
    }

    public RattyMenuFailedEvent(String reason) {
        super(new Exception(reason));
    }
}
