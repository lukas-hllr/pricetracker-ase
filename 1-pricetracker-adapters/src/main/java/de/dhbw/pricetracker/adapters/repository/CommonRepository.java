package de.dhbw.pricetracker.adapters.repository;

import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.application.repository.DuplicateException;
import de.dhbw.pricetracker.application.repository.NotFoundException;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Price;
import de.dhbw.pricetracker.domain.Product;
import de.dhbw.pricetracker.plugins.storage.CsvPlatformStorage;
import de.dhbw.pricetracker.plugins.storage.CsvPriceStorage;
import de.dhbw.pricetracker.plugins.storage.CsvProductStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonRepository implements Repository {

    private Storage<Platform> platformStorage;
    private Storage<Product> productStorage;
    private Storage<Price> priceStorage;
    private Map<String, Platform> platforms;
    private Map<String, Product> products;
    private List<Price> prices;

    public CommonRepository()
    {
        this.platformStorage = new CsvPlatformStorage();
        this.productStorage = new CsvProductStorage();
        this.priceStorage = new CsvPriceStorage();
        initPlatformRepository();
        initProductRepository();
        initPriceRepository();
    }
    public CommonRepository(Storage<Platform> platformStorage, Storage<Product> productStorage){
        this.platformStorage = platformStorage;
        this.productStorage = productStorage;
        initPlatformRepository();
        initProductRepository();
    }
    private void initPlatformRepository()
    {
        List<Platform> platformList = platformStorage.getAll();
        platforms = new HashMap<>();
        for (Platform platform: platformList) {
            platforms.put(platform.getName(), platform);
        }
    }

    private void initProductRepository()
    {
        List<Product> productList = productStorage.getAll();
        products = new HashMap<>();
        for (Product product: productList) {
            products.put(product.getName(), product);
        }
    }

    private void initPriceRepository()
    {
        prices = priceStorage.getAll();
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
        if(platforms.containsKey(platform.getName())){
            List<Product> toBeRemoved = new ArrayList<>();
            for (Map.Entry<String, Product> productEntry : products.entrySet()) {
                Product product = productEntry.getValue();
                if(product.getPlatform().equals(platform.getName())){
                    toBeRemoved.add(product);
                }
            }
            for(Product product: toBeRemoved){
                removeProduct(product);
            }
            this.platformStorage.remove(platform);
            platforms.remove(platform.getName());
        } else {
            throw new NotFoundException(platform);
        }
    }

    @Override
    public List<Platform> getAllPlatforms() {
        return new ArrayList<>(platforms.values());
    }

    @Override
    public void addProduct(Product entity) throws DuplicateException, NotFoundException {
        if(products.containsKey(entity.getName())){
            throw new DuplicateException(entity);
        }
        if(!platforms.containsKey(entity.getPlatform())){
            throw new NotFoundException(Platform.class, entity.getPlatform());
        }

        this.products.put(entity.getName(), entity);
        this.productStorage.add(entity);
    }

    @Override
    public void addPrice(Price price) throws NotFoundException
    {
        if(!products.containsKey(price.product())){
            throw new NotFoundException(Product.class, price.product());
        }

        this.prices.add(price);
        this.priceStorage.add(price);

    }

    @Override
    public void removeProduct(Product product) throws NotFoundException {
        if(products.containsKey(product.getName())){
            this.productStorage.remove(product);
            products.remove(product.getName());
        } else {
            throw new NotFoundException(product);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Price> getPricesOfProduct(Product product)
    {
        List<Price> pricesOfProduct = new ArrayList<>();
        for (Price price : prices) {
            if(price.product().equals(product.getName())){
                pricesOfProduct.add(price);
            }
        }
        return pricesOfProduct;
    }
}
