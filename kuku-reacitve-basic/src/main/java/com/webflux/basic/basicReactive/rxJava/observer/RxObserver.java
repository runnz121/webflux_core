package com.webflux.basic.basicReactive.rxJava.observer;

/**
 * 리엑티브 스트림 관찰자 패턴
 *
 * @param <T>
 */
public interface RxObserver<T> {

    void onNext(T next);
    void onComplete();
    void onError(Exception e);
}
