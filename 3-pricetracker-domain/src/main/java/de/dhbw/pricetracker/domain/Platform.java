package de.dhbw.pricetracker.domain;

import java.util.List;

public interface Platform {
    public String getName();
    public String getPriceSelector();
    public List<Product> getProducts();
}
