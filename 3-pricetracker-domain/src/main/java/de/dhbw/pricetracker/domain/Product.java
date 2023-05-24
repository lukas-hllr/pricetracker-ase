package de.dhbw.pricetracker.domain;

import java.util.List;

public interface Product {
    public String getName();
    public String getURL();
    public List<Price> getPrices();
}
