package de.dhbw.pricetracker.application.repository;

import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Price;
import de.dhbw.pricetracker.domain.Product;

import java.util.List;
import java.util.Map;

public interface Repository {
    public void addPlatform(Platform platform) throws DuplicateException;
    public void addProduct(Product product) throws DuplicateException, NotFoundException;
    public void addPrice(Price price) throws NotFoundException;
    public void removePlatform(Platform platform) throws NotFoundException;
    public void removeProduct(Product product) throws NotFoundException;

    public List<Platform> getAllPlatforms();
    public List<Product> getAllProducts();
    public List<Price> getPricesOfProduct(Product product);
}
