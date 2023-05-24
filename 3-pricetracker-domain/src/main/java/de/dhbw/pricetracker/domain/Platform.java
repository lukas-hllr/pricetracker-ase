package de.dhbw.pricetracker.domain;

public class Platform {
    String name;
    String nameIdentifier;
    String priceIdentifier;

    public Platform(String name, String nameIdentifier, String priceIdentifier) {
        this.name = name;
        this.nameIdentifier = nameIdentifier;
        this.priceIdentifier = priceIdentifier;
    }
}
