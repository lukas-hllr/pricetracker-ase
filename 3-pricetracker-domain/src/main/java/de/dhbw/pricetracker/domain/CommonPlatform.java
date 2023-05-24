package de.dhbw.pricetracker.domain;

import java.util.ArrayList;
import java.util.List;

public class CommonPlatform implements Platform {
    private String name;
    private String priceIdentifier;
    private List<Product> products;

    public CommonPlatform(String name, String priceIdentifier) {
        this.name = name;
        this.priceIdentifier = priceIdentifier;
        this.products = new ArrayList();
    }

    public CommonPlatform(String name, String priceIdentifier, List<Product> products) {
        this.name = name;
        this.priceIdentifier = priceIdentifier;
        this.products = products;
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
