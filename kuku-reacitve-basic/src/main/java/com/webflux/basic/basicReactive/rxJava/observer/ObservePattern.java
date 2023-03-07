package com.webflux.basic.basicReactive.rxJava.observer;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import rx.Observable;

public class ObservePattern {


    // since java 9 deprecated
    public static Observable rxObserve() {

        // rx 3.x
        return Observable.<String>create(
            sub -> {
                sub.onNext("Hello reactive");
                sub.onCompleted();
            }
        );

    }

    public static Subscriber rxSubscribe() {

        return new Subscriber<String>() {

            @Override
            public void onSubscribe(Subscription s) {
                System.out.println(s);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t);
            }

            @Override
            public void onComplete() {
                System.out.println("Done!");
            }
        };
    }

}
