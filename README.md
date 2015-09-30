# Ratty
See what's for dinner on your android device.


The networking works like this:

1. A request is made to the Api class. This allows for typesafe calls to be made.
2. The Api class dispatches a NetworkThread object, which performs the network call in a background thread.

In the meantime, it's possible that our objects will be destroyed (such as during a device orientation change).
To counteract this, I use the otto eventbus to 'post' the result of the network call once it's finished.

3. [BACKGROUND] The Api call finishes, and the result is downloaded.
4. [BACKGROUND] A consumer is invoked, to consume the response.

A consumer is just an object that deals with the result of an Api call. This abstraction is present so that different
behavior can be easily swapped in for the same network call. 

For example, you might have one implementation of a consumer that simply passes things back using the eventbus.
Later, when you add a database to your application, you could implement a new consumer class which performs database insertions
and then invokes the other consumer (thus forming a chain of consumers).
I chose to use this pattern to be hopeful of Java 8 coming to Android (Consumers can eventually become lambdas!)

5. The consumer finishes processing, and posts an event to the app's event bus. All subscribing activities receive this event
and can deal with the result of the Api call. No weak references, No dangling references, just an event bus and a dream.

