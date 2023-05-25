package de.dhbw.pricetracker.domain;

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
}
