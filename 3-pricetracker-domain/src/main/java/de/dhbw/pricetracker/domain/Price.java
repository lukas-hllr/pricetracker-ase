package de.dhbw.pricetracker.domain;

import java.time.LocalDate;

public class Price {
    private LocalDate timeStamp;
    private double value;

    public Price(LocalDate timeStamp, double value) {
        this.timeStamp = timeStamp;
        this.value = value;
    }
}
