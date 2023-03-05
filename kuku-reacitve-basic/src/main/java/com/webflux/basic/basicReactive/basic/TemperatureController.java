package com.webflux.basic.basicReactive.basic.temperature;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class TemperatureController {

    // SSE : https://tecoble.techcourse.co.kr/post/2022-10-11-server-sent-events/
    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @RequestMapping(
        value = "/temperature-stream",
        method = RequestMethod.GET
    )
    public SseEmitter events(HttpServletRequest request) {
        SseEmitter emitter = new SseEmitter();
        clients.add(emitter);

        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onCompletion(() -> clients.remove(emitter));
        return emitter;
    }

    @Async
    @EventListener
    public void handleMessage(com.webflux.basic.basicReactive.basic.temperature.Temperature temperature) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        clients.forEach(emitter -> {
            try {
                emitter.send(temperature, MediaType.APPLICATION_JSON); // -> error callback 가능
            } catch (Exception ignore) {
                deadEmitters.add(emitter);
            }
        });
        deadEmitters.forEach(clients::remove);
    }
}
