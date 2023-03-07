package com.webflux.basic.basicReactive.basic.temperatureRxJava;

import java.io.IOException;


import org.reactivestreams.Subscription;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import rx.Subscriber;

public class RxSeeEmitter extends SseEmitter {

    static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private final Subscriber<TemperatureRx> subscriber;

    public RxSeeEmitter() {
        super(SSE_SESSION_TIMEOUT);
        this.subscriber = new Subscriber<TemperatureRx>() {

            @Override
            public void onNext(TemperatureRx temperatureRx) {
                try {
                    RxSeeEmitter.this.send(temperatureRx);
                } catch (IOException io) {
                    unsubscribe();
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable t) {

            }

        };

        onCompletion(subscriber::unsubscribe);
        onTimeout(subscriber::unsubscribe);
    }

    Subscriber<TemperatureRx> getSubscriber() {
        return subscriber;
    }
}
