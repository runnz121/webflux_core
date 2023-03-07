package com.webflux.basic.basicReactive.basic.temperatureRxJava;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import rx.Observable;

@Component // 스프링 빈으로 등록 -> 다른 빈에서 자동 탐색 가능
public class TemperatureSensorRx {

    private final Random rnd = new Random();

    private final Observable<TemperatureRx> dataStream =
        Observable
            .range(0, Integer.MAX_VALUE)
            .concatMap(tick -> Observable
                .just(tick)
                .delay(rnd.nextInt(5000), TimeUnit.MILLISECONDS)
                .map(tickValue -> this.probe()))
            .publish()
            .refCount();

    private TemperatureRx probe() {
        return new TemperatureRx(16 + rnd.nextGaussian() * 10);
    }

    public Observable<TemperatureRx> temperatureStream() {
        return dataStream;
    }
}
