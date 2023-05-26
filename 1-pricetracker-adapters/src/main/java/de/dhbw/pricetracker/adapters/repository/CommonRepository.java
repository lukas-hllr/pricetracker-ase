package de.dhbw.pricetracker.adapters.repository;

import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.application.repository.DuplicateException;
import de.dhbw.pricetracker.application.repository.NotFoundException;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;
import de.dhbw.pricetracker.plugins.storage.CsvPlatformStorage;
import de.dhbw.pricetracker.plugins.storage.CsvProductStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonRepository implements Repository {

    private Storage<Platform> platformStorage;
    private Storage<Product> productStorage;
    private Map<String, Platform> platforms;
    private Map<String, Product> products;

    public CommonRepository()
    {
        this.platformStorage = new CsvPlatformStorage();
        this.productStorage = new CsvProductStorage();
        initPlatformRepository();
        initProductRepository();
    }
    public CommonRepository(Storage<Platform> platformStorage, Storage<Product> productStorage){
        this.platformStorage = platformStorage;
        this.productStorage = productStorage;
        initPlatformRepository();
    }
    private void initPlatformRepository()
    {
        List<Platform> platformList = platformStorage.getAll();
        platforms = new HashMap();
        for (Platform platform: platformList) {
            platforms.put(platform.getName(), platform);
        }
    }

    private void initProductRepository()
    {
        List<Product> productList = productStorage.getAll();
        products = new HashMap();
        for (Product product: productList) {
            products.put(product.getName(), product);
        }
    }

    @Override
    public void addPlatform(Platform platform) throws DuplicateException {
        if(platforms.containsKey(platform.getName())){
            throw new DuplicateException(platform);
        }

        this.platforms.put(platform.getName(), platform);
        this.platformStorage.add(platform);
    }

    @Override
    public void removePlatform(Platform platform) throws NotFoundException {

    }

    @Override
    public Map<String, Platform> getAllPlatforms() {
        return platforms;
    }

    @Override
    public void addProduct(Product entity) throws DuplicateException, NotFoundException {
        if(products.containsKey(entity.getName())){
            throw new DuplicateException(entity);
        }
        if(!platforms.containsKey(entity.getPlatform())){
            throw new NotFoundException(entity.getPlatform());
        }

        this.products.put(entity.getName(), entity);
        this.productStorage.add(entity);
    }

    @Override
    public void removeProduct(Product product) throws NotFoundException {
        this.productStorage.remove(product);
    }

    @Override
    public Map<String, Product> getAllProducts() {
        return products;
    }
}