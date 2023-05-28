package de.dhbw.pricetracker.application.ui;

import de.dhbw.pricetracker.domain.Currency;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Price;
import de.dhbw.pricetracker.domain.Product;

import java.util.List;
import java.util.Map;

public interface UserInterface
{
    public void start();

    public void setUIEventListener(UIEventListener listener);

    public void helpForAddPlatformEvent();

    public void addPlatformEvent();

    public void removePlatformEvent();

    public void helpForAddProductEvent();

    public void addProductEvent();

    public void removeProductEvent();

    public void listProductsEvent(List<Product> products);

    public void listPlatformsEvent(List<Platform> platforms);

    public void listPricesEvent(List<Price> prices);

    public void listCurrenciesEvent(List<Currency> currencies);

    public void onUpdateStartedEvent();

    public void onUpdateStartedEvent(Product product);

    public void onPriceIncreased(Price newPrice, Product product);

    public void onPriceDecreased(Price newPrice, Product product);

    public void onNoPriceChange(Product product);

    public void onSuccess();

    public void onError(Exception e);

    public Product onRequestProduct(List<Product> allProducts);
}
