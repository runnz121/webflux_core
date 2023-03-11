package com.webflux.basic.basicReactive.reactiveStream.inConsistencyApi;

import java.util.concurrent.CompletionStage;

public interface AsyncDatabaseClient {

    <T>CompletionStage<T> store(CompletionStage<T> stage);
}
