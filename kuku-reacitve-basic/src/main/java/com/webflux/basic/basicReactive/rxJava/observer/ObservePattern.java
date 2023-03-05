package com.webflux.basic.basicReactive.rxJava.observer;

import java.util.concurrent.Flow;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public class ObservePattern {


    // since java 9 deprecated
    public static Observable rxObserve() {

        // rx 3.x
        return Observable.<String>create(
            sub -> {
                sub.onNext("Hello reactive");
                sub.onComplete();
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
