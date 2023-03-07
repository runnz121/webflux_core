package com.webflux.basic.basicReactive.basic.temperatureRxJava;

final class TemperatureRx {

    private final double value;

    TemperatureRx(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
}
