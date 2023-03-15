package com.webflux.basic.rxJava;

import ch.qos.logback.core.util.TimeUtil;
import io.reactivex.rxjava3.core.BackpressureOverflowStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.net.InterfaceAddress;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import scala.Int;

public class RxJavaTest {


    /**
     * 구독할 때마다 데이터를 통지하는 다른 타임라인이 생성된다
     */
    @Test
    void coldPublisherTest() {
        Flowable<Integer> flowable = Flowable.just(1, 2, 3, 4);

        flowable.subscribe(data -> System.out.println("구독데이터 1 : " + data));
        flowable.subscribe(data -> System.out.println("구독데이터 2 : " + data));

    }

    /**
     * - 생산자는 소비자 수와 별개로 데이터는 한번만 통지되는 방식으로 데이터 통지 타임 라인은 하나다
     * - 소비자는 구독시점에 통지되고있는 데이터만 전달받을 수 있다
     */
    @Test
    void hotPublisherTest() {
        PublishProcessor<Integer> processor = PublishProcessor.create();

        // 처음부터 구독해서 1,2,3,4 모두 다 받는다
        processor.subscribe(data -> System.out.println("구독데이터 1 : " + data));
        processor.onNext(1);
        processor.onNext(2);

        // 3번부터 구독해서 3, 4만 받는다
        processor.subscribe(data -> System.out.println("구독데이터 2: " + data));
        processor.onNext(3);
        processor.onNext(4);

        processor.onComplete();
    }

    /**
     * 배압의 잘못된 예시코드
     *
     * 생산자쪽에서 통지한 데이터는 RxComputationThreadPool-2 Thread에서 실행이 되고
     * 소비자쪽에서 처리하는 부분 은 RxComputationThreadPool-1 Thread에서 각각 실행을 하게 된다
     *
     * doOnNext -> 생산자 쪽에서 데이터를 빠르게 생성
     * subscribe -> threadSleep으로 인해 느리게 처리됨
     *
     * // 에러 발생
     * io.reactivex.rxjava3.exceptions.MissingBackpressureException: Could not emit value 128 due to lack of requests
     *
     * @throws InterruptedException
     */
    @Test
    void backPressureTest1() throws InterruptedException {
        Flowable.interval(1L, TimeUnit.MILLISECONDS)
            .doOnNext(data -> System.out.println("doNext : " +  data))
            .observeOn(Schedulers.computation())
            .subscribe(
                data -> {
                    System.out.println("# 소바지 처리 대기종");
                    Thread.sleep(100L);
                    System.out.println("data : " + data);
                },
                error -> System.out.println("error : " + error),
                () -> System.out.println("compelte!")
            );
        Thread.sleep(2000L);
    }

    /**
     * 배압 전략
     * DROP_LATEST 전략
     *  - 버퍼가 가득 찬 시점에 가장 최근에 들어온 데이터를 drop 한다
     *  - drop 된 빈 자리에 버퍼 밖에서 대기하던 데이터를 채운다
     */

    @Test
    void backPressureDropLatestStrategy() throws InterruptedException {
        Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .doOnNext(data -> System.out.println("#interval doOnNext : " + data))
            .onBackpressureBuffer(
                2,
                () -> System.out.println("over flow !"),
                BackpressureOverflowStrategy.DROP_LATEST) // 배압 전략 설정
            .doOnNext(data -> System.out.println("#onBAckPressureBuffer doOnNext : " + data))
            .observeOn(Schedulers.computation() , false,  1)
            .subscribe(
                data -> {
                    Thread.sleep(1000L);
                    System.out.println("OnNext : " + data);
                },
                error -> System.out.println("OnError : " + error)
            );
        Thread.sleep(2000L);
    }

}
