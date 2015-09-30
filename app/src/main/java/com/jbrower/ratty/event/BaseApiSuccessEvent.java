package com.jbrower.ratty.event;

/**
 * This is a useless mirror class to {@link BaseApiFailureEvent}. More default behavior can be
 * added here.
 * Created by Justin on 9/3/15.
 */
public class BaseApiSuccessEvent<T> extends ApiEvent<T> {
    public BaseApiSuccessEvent(T result) {
        super(result);
    }
}
