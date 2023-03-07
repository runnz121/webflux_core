package com.webflux.basic.basicReactive.basic.temperature;

public final class Temperature {
    private final double value;

    public Temperature(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }
}
