package de.dhbw.pricetracker.application.ui;

import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

import java.util.List;
import java.util.Map;

public interface UserInterface {
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

    public void onUpdateStartedEvent();
    public void onUpdateStartedEvent(Product product);
    public void onPriceIncreased(double newPrice, Product product);
    public void onPriceDecreased(double newPrice, Product product);
    public void onNoPriceChange(Product product);

    public void info(String message);
    public void success(String message);
    public void error(String message);
    public void warning(String message);
}
