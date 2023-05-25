package de.dhbw.pricetracker.domain;

import java.util.List;

public interface Product {
    public String getName();
    public String getURL();
    public String getPlatform();
    public double getPrice();
    public void setPrice(double price);
}
