package de.dhbw.pricetracker.application.repository;

import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

import java.util.Map;

public interface Repository {
    public void addPlatform(Platform platform) throws DuplicateException;
    public void addProduct(Product product) throws DuplicateException, NotFoundException;
    public void removePlatform(Platform platform) throws NotFoundException;
    public void removeProduct(Product product) throws NotFoundException;

    public Map<String, Platform> getAllPlatforms();
    public Map<String, Product> getAllProducts();
}
