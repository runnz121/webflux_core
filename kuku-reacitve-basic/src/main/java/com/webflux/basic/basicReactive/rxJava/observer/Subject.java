package com.webflux.basic.basicReactive.rxJava.observer;

/**
 * 이벤트 프로듀서
 *
 * @param <T>
 */
public interface Subject<T> {

    void registerObserve(Observer<T> observer);
    void unregisterObserver(Observer<T> observer);
    void notifyObservers(T event);
}
