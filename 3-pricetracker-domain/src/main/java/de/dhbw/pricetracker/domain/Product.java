package de.dhbw.pricetracker.domain;

import java.util.Objects;

public class Product
{
    String name;
    String url;
    String platform;
    Price price;
    Currency currency;

    public Product(String name, String platform, String url, Currency currency)
    {
        this.name = name;
        this.url = url;
        this.platform = platform;
        this.currency = currency;
        this.price = new Price(name, currency);
    }

    public Product(String name, String platform, String url, Price price)
    {
        this.name = name;
        this.url = url;
        this.platform = platform;
        this.currency = price.currency();
        this.price = price;
    }

    public String name()
    {
        return this.name;
    }

    public String url()
    {
        return this.url;
    }

    public String platform()
    {
        return this.platform;
    }

    public Currency currency()
    {
        return this.currency;
    }

    public Price price()
    {
        return this.price;
    }

    public void setPrice(Price price)
    {
        this.price = price;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url) && Objects.equals(platform, that.platform);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, url, platform);
    }
}
