package com.webflux.basic.basicReactive.basic.temperatureRxJava;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import jakarta.servlet.http.HttpServletRequest;

@RestController
public class TemperatureRxController  {

    private final TemperatureSensorRx temperatureSensorRx;

    public TemperatureRxController(
        TemperatureSensorRx temperatureSensorRx) {
        this.temperatureSensorRx = temperatureSensorRx;
    }

    @RequestMapping(
        value = "/temperatureRx-stream",
        method = RequestMethod.GET
    )
    public SseEmitter events(HttpServletRequest request) {
        RxSeeEmitter emitter = new RxSeeEmitter();

        temperatureSensorRx.temperatureStream().subscribe(emitter.getSubscriber());

        return emitter;
    }
}


