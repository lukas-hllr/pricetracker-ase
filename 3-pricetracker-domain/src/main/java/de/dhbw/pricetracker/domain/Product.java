package de.dhbw.pricetracker.domain;

import java.util.ArrayList;

public class Product {

    String name;

    String url;
    Platform platform;
    double notificationThreshold;
    ArrayList<Price> prices;

    public Product(String name, String url, Platform platform, double notificationThreshold) {
        this.name = name;
        this.url = url;
        this.platform = platform;
        this.notificationThreshold = notificationThreshold;
    }
}
