package de.dhbw.pricetracker.domain;

import java.time.LocalDate;

public class CommonPrice implements Price {
    private LocalDate timeStamp;
    private double value;

    public CommonPrice(LocalDate timeStamp, double price) {
        this.timeStamp = timeStamp;
        this.value = value;
    }

    @Override
    public double getPrice() {
        return this.value;
    }

    @Override
    public LocalDate getTimestamp() {
        return this.timeStamp;
    }
}
