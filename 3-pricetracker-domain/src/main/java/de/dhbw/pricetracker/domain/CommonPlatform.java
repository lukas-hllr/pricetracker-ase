package de.dhbw.pricetracker.domain;

import java.util.List;

public class CommonPlatform implements Platform {
    private String name;
    private String priceIdentifier;
    private List<Product> products;

    public CommonPlatform(String name, String priceIdentifier) {
        this.name = name;
        //this.nameIdentifier = nameIdentifier;
        this.priceIdentifier = priceIdentifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPriceSelector() {
        return this.priceIdentifier;
    }

    @Override
    public List<Product> getProducts() {
        return this.products;
    }
}
