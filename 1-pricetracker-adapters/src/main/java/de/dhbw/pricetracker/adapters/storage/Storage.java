package de.dhbw.pricetracker.adapters.storage;

import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

public interface Storage {

    //public List<Platform> getPlatforms();
    //public List<Product> getProducts();
    public void addPlatform(Platform platform);
    //public void deletePlatform(String name);
    //public Platform getPlatform(String name);

    public void addProduct(Product product);
    //public void deleteProduct(String productName, String platformName);
    //public void addPrice();

}
