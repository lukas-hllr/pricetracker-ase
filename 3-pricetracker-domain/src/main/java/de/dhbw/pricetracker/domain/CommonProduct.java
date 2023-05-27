package de.dhbw.pricetracker.domain;

import java.util.Objects;

public class CommonProduct implements Product
{
    String name;
    String url;
    String platform;
    Price price;
    Currency currency;

    public CommonProduct(String name, String platform, String url, Currency currency)
    {
        this.name = name;
        this.url = url;
        this.platform = platform;
        this.currency = currency;
        this.price = new Price(name, currency);
    }

    public CommonProduct(String name, String platform, String url, Price price)
    {
        this.name = name;
        this.url = url;
        this.platform = platform;
        this.currency = price.currency();
        this.price = price;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getURL()
    {
        return this.url;
    }

    @Override
    public String getPlatform()
    {
        return this.platform;
    }

    @Override
    public Currency getCurrency()
    {
        return this.currency;
    }

    @Override
    public Price getPrice()
    {
        return this.price;
    }

    @Override
    public void setPrice(Price price)
    {
        this.price = price;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonProduct that = (CommonProduct) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url) && Objects.equals(platform, that.platform);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, url, platform);
    }
}
