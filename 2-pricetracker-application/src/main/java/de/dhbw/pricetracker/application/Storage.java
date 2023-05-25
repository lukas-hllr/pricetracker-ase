package de.dhbw.pricetracker.application;

import de.dhbw.pricetracker.adapters.repository.DuplicateException;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;
import de.dhbw.pricetracker.adapters.repository.NotFoundException;

import java.util.Map;

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
