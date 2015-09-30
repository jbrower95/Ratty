package com.jbrower.ratty.network;

import android.text.TextUtils;
import android.util.Log;

import com.jbrower.ratty.consumer.Consumer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.util.concurrent.atomic.AtomicBoolean;

enum RequestType {
    METHOD_GET,
    METHOD_PUT,
    METHOD_POST
};

/**
 * A thread for making network connections and reporting them back.
 * Created by Justin on 8/31/15.
 */
public class NetworkThread implements Runnable {

    private static final String TAG = NetworkThread.class.getName();

    private Route endPoint;
    private String url;
    private String jsonToSend;
    private String uuid;
    private RequestType method;
    private Consumer consumer;
    private HttpUriRequest mRequest;
    private Thread mThread;
    private AtomicBoolean mAbortFlag = new AtomicBoolean(false);

    public static class Builder {

        private Route mRoute = Route.UNSPECIFIED;
        private String mUrl;
        private String mJsonToSend;
        private String mUuid;
        private RequestType mMethod;
        private Consumer mConsumer;

        /**
         * This is a non-optional field identifying which route this request is for.
         * @param route The route you are requesting.
         * @return A builder for chaining.
         */
        public Builder withRoute(final Route route) {
            mRoute = route;
            return this;
        }

        /**
         * The {@link Consumer} to consume the response.
         * @param consumer The consumer.
         * @return A builder for chaining.
         */
        public Builder withConsumer(final Consumer consumer) {
            mConsumer = consumer;
            return this;
        }

        /**
         * A unique device ID. This will be sent as an X-Device-ID
         * HTTP header.
         * @param uuid The uuid of the device.
         * @return A builder for chaining.
         */
        public Builder withUuid(final String uuid) {
            mUuid = uuid;
            return this;
        }

        /**
         * Identifies this request as a GET request for the given URL.
         * @param url The url to GET.
         * @return A builder for chaining.
         */
        public Builder get(final String url) {
            mUrl = url;
            mMethod = RequestType.METHOD_GET;
            mJsonToSend = null;
            return this;
        }

        /**
         * Identifies this request as a PUT request for the given URL.
         * @param url The url to PUT to.
         * @param json The json to post.
         * @return A builder for chaining.
         */
        public Builder put(final String url, final String json) {
            mUrl = url;
            mMethod = RequestType.METHOD_PUT;
            mJsonToSend = json;
            return this;
        }

        /**
         * Identifies this request as a POST request for the given URL.
         * @param url The url to POST to.
         * @param json The json to post.
         * @return A builder for chaining.
         */
        public Builder post(final String url, final String json) {
            mUrl = url;
            mMethod = RequestType.METHOD_POST;
            mJsonToSend = json;
            return this;
        }

        /**
         * Creates the network thread from the builder.
         * This will throw an {@link IllegalArgumentException} if
         * the builder was not configured properly.
         * @return A valid, ready to go network thread.
         */
        public NetworkThread build() {
            final NetworkThread thread = new NetworkThread();

            if (TextUtils.isEmpty(mUrl)) {
                throw new IllegalArgumentException("Url cannot be null in a network request.");
            }

            if (mRoute == Route.UNSPECIFIED) {
                throw new IllegalArgumentException("Must specify a route for connection.");
            }

            thread.endPoint = mRoute;
            thread.url = mUrl;
            thread.jsonToSend = mJsonToSend;
            thread.uuid = mUuid;
            thread.method = mMethod;
            thread.consumer = mConsumer;

            return thread;
        }
    }

    /**
     * Cancels the network request.
     */
    public void cancel() throws UnsupportedOperationException {
        if (mRequest != null) {
            // the thread is already chugging along.
            mRequest.abort();
        } else {
            synchronized (mAbortFlag) {
                mAbortFlag.set(true);
            }
        }
    }

    /**
     * Determines whether or not a {@link NetworkThread} is running.
     * @param thread The thread to check.
     * @return True if it is running.
     */
    public static boolean isRunning(NetworkThread thread) {
        return (thread != null && thread.mThread.isAlive());
    }

    @Override
    public void run() {

        try {
            Log.d(TAG, "Getting url: " + url);

            final HttpClient client = new DefaultHttpClient();

            final StringEntity jsonEntity;
            if (!TextUtils.isEmpty(jsonToSend)) {
                jsonEntity = new StringEntity(jsonToSend);
                jsonEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } else {
                jsonEntity = null;
            }

            if (mAbortFlag.get()) {
                Log.d(TAG, "ABORTING");
                return;
            }

            switch (method) {
                case METHOD_GET:
                    mRequest = new HttpGet(url);
                    break;
                case METHOD_PUT:
                    mRequest = new HttpPut(url);
                    if (jsonEntity != null) {
                        ((HttpPut) mRequest).setEntity(jsonEntity);
                    }
                    break;
                case METHOD_POST:
                default:
                    mRequest = new HttpPost(url);
                    if (jsonEntity != null) {
                        ((HttpPost) mRequest).setEntity(jsonEntity);
                    }
                    break;
            }

            mRequest.setHeader("Accept", "application/json");
            mRequest.setHeader("Content-Type", "application/json");

            if (uuid != null) {
                mRequest.setHeader("X-Device-ID", uuid);
            }

            if (mAbortFlag.get()) {
                Log.d(TAG, "ABORTING");
                return;
            }

            Log.d(TAG, "Executing request...");

            final HttpResponse response = client.execute(mRequest);
            final HttpEntity responseObject = response.getEntity();

            if (mAbortFlag.get()) {
                Log.d(TAG, "ABORTING");
                return;
            }

            final String result = NetworkUtils.consumeInputStream(responseObject.getContent());

            Log.d(TAG, "Got response: " + result);


            if (mAbortFlag.get()) {
                Log.d(TAG, "ABORTING");
                return;
            }

            if (result != null) {
                consumer.consume(result, response.getStatusLine().getStatusCode());
            } else {
                consumer.failed(response.getStatusLine().getStatusCode());
            }
        } catch (Exception e){
            consumer.failed(e);
        }
    }

    /**
     * Starts the network thread. DO NOT USE @RUN directly or you
     * won't be able to stop this.
     */
    public NetworkThread start() {
        mThread = new Thread(this);
        mThread.start();
        return this;
    }

}
