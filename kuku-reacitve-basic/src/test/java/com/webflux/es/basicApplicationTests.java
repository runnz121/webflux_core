package com.webflux.es;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class basicApplicationTests {

	public <T, R> List<R> map(List<T> list, Function<T, R> f) {

		List<R> result = new ArrayList<>();

		for(T t : list) {
			result.add(f.apply(t)); //
		}

		return result;
	}

	@Test
	void functionalTest() {

		List<Integer> l = map(
			Arrays.asList("lambdas", "in", "action"), // list
			String::length // 각 길이를 갖고옴 -> f.apply(String 길이 반환)
		);

		System.out.println(l);
	}

}
