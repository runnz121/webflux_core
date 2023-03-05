package com.webflux.basic.basicReactive.rxJava.observer;

/**
 * 이벤트 컨슈머
 *
 * @param <T>
 */
public interface Observer<T> {
    void notify(T event);
}
