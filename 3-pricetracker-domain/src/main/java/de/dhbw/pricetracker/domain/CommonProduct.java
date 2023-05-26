package de.dhbw.pricetracker.domain;

import java.util.Objects;

public class CommonProduct implements Product{
    String name;

    String url;

    String platform;

    double price;

    public CommonProduct(String name, String platform, String url) {
        this.name = name;
        this.url = url;
        this.platform = platform;
        this.price = 0.0;
    }

    public CommonProduct(String name, String platform, String url, double price) {
        this.name = name;
        this.url = url;
        this.platform = platform;
        this.price = price;
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
    public String getPlatform(){
        return this.platform;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(double price) {
        if(price > 0.0){
            this.price = price;
        } else {
            this.price = 0.0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonProduct that = (CommonProduct) o;
        return Objects.equals(name, that.name) && Objects.equals(url, that.url) && Objects.equals(platform, that.platform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, platform);
    }
}
