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

public class CommonRepository implements Repository
{

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

    public CommonRepository(Storage<Platform> platformStorage, Storage<Product> productStorage)
    {
        this.platformStorage = platformStorage;
        this.productStorage = productStorage;
        initPlatformRepository();
        initProductRepository();
    }

    private void initPlatformRepository()
    {
        List<Platform> platformList = platformStorage.getAll();
        platforms = new HashMap<>();
        for (Platform platform : platformList) {
            platforms.put(platform.name(), platform);
        }
    }

    private void initProductRepository()
    {
        List<Product> productList = productStorage.getAll();
        products = new HashMap<>();
        for (Product product : productList) {
            products.put(product.name(), product);
        }
    }

    private void initPriceRepository()
    {
        prices = priceStorage.getAll();
    }

    @Override
    public void addPlatform(Platform platform) throws DuplicateException
    {
        if (platforms.containsKey(platform.name())) {
            throw new DuplicateException(platform);
        }

        this.platforms.put(platform.name(), platform);
        this.platformStorage.add(platform);
    }

    @Override
    public void removePlatform(Platform platform) throws NotFoundException
    {
        if (platforms.containsKey(platform.name())) {
            List<Product> toBeRemoved = new ArrayList<>();
            for (Map.Entry<String, Product> productEntry : products.entrySet()) {
                Product product = productEntry.getValue();
                if (product.platform().equals(platform.name())) {
                    toBeRemoved.add(product);
                }
            }
            for (Product product : toBeRemoved) {
                removeProduct(product);
            }
            this.platformStorage.remove(platform);
            platforms.remove(platform.name());
        } else {
            throw new NotFoundException(platform);
        }
    }

    @Override
    public List<Platform> getAllPlatforms()
    {
        return new ArrayList<>(platforms.values());
    }

    @Override
    public void addProduct(Product entity) throws DuplicateException, NotFoundException
    {
        if (products.containsKey(entity.name())) {
            throw new DuplicateException(entity);
        }
        if (!platforms.containsKey(entity.platform())) {
            throw new NotFoundException(Platform.class, entity.platform());
        }

        this.products.put(entity.name(), entity);
        this.productStorage.add(entity);
    }

    @Override
    public void addPrice(Price price) throws NotFoundException
    {
        if (!products.containsKey(price.product())) {
            throw new NotFoundException(Product.class, price.product());
        }

        this.prices.add(price);
        this.priceStorage.add(price);

    }

    @Override
    public void removeProduct(Product product) throws NotFoundException
    {
        if (products.containsKey(product.name())) {
            this.productStorage.remove(product);
            products.remove(product.name());
        } else {
            throw new NotFoundException(product);
        }
    }

    @Override
    public Platform getPlatform(Product product)
    {
        return platforms.get(product.platform());
    }

    @Override
    public List<Product> getAllProducts()
    {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Price> getPricesOfProduct(Product product)
    {
        List<Price> pricesOfProduct = new ArrayList<>();
        for (Price price : prices) {
            if (price.product().equals(product.name())) {
                pricesOfProduct.add(price);
            }
        }
        return pricesOfProduct;
    }
}
