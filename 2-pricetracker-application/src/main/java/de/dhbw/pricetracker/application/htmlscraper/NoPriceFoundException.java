package de.dhbw.pricetracker.application.htmlscraper;

public class NoPriceFoundException extends Exception {
    public NoPriceFoundException() {
        super("Could not find a price.");
    }
}
