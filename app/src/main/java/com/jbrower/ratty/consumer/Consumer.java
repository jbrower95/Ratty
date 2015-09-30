package com.jbrower.ratty.consumer;

/**
 * Represents a consumer of a network response. Consumers run on a background thread
 * and contain several handler methods to parse data before delivering it.
 * Created by Justin on 8/31/15.
 */
public interface Consumer {
    /**
     * Tells the consumer to consume a response. This could lead (potentially) to a db insert or an otto bus post.
     * @param response The response to parse.
     * @param responseCode the server response code (500, etc.)
     */
    void consume(String response, int responseCode);

    /**
     * Tells the consumer that an error has occurred.
     * @param e The exception that occurred.
     */
    void failed(Exception e);

    /**
     * Tells the consumer that an error occurred during normal
     * execution.
     */
    void failed(int statusCode);
}
