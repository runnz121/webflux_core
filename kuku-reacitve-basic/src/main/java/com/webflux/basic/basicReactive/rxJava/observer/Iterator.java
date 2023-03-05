package com.webflux.basic.basicReactive.rxJava.observer;

/**
 * 동기식 반복자 패턴
 *
 * @param <T>
 */
public interface Iterator<T> {

    T next();
    boolean hasNext();
}
