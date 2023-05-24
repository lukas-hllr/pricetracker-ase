package de.dhbw.pricetracker.domain;

import java.time.LocalDate;

public interface Price {
    public double getPrice();
    public LocalDate getTimestamp();
}
