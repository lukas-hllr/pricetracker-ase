package de.dhbw.pricetracker.domain;

import java.util.ArrayList;
import java.util.List;

public class CommonProduct implements Product{
    String name;
    String url;
    List<Price> prices;

    public CommonProduct(String name, String url) {
        this.name = name;
        this.url = url;
        this.prices = new ArrayList();
    }

    public CommonProduct(String name, String url, List<Price> prices) {
        this.name = name;
        this.url = url;
        this.prices = prices;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getURL() {
        return this.url;
    }

    @Override
    public List<Price> getPrices() {
        return this.prices;
    }
}
