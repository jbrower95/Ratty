package com.jbrower.ratty.event;

import com.jbrower.ratty.model.RattyMenu;

/**
 * Signifies that the ratty menu was received.
 * Created by Justin on 9/29/15.
 */
public class RattyMenuReceivedEvent extends BaseApiSuccessEvent<RattyMenu> {
    public RattyMenuReceivedEvent(RattyMenu menu) {
        super(menu);
    }
}
