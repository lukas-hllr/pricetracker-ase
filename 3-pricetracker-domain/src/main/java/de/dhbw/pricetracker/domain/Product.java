package de.dhbw.pricetracker.domain;

import java.util.List;

public interface Product
{
    public String getName();

    public String getURL();

    public String getPlatform();

    public Currency getCurrency();

    public Price getPrice();

    public void setPrice(Price price);
}
