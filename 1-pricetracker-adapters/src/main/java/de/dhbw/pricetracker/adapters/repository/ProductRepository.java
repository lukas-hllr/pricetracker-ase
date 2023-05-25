package de.dhbw.pricetracker.adapters.repository;

import de.dhbw.pricetracker.application.repository.DuplicateException;
import de.dhbw.pricetracker.application.repository.NotFoundException;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;
import de.dhbw.pricetracker.plugins.storage.CsvProductStorage;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository implements Repository<Product> {

    private Storage<Product> productStorage;
    private Repository<Platform> platformRepository;
    private Map<String, Product> products;

    public ProductRepository(Repository<Platform> platformRepository){
        this.productStorage = new CsvProductStorage();
        this.platformRepository = platformRepository;
        this.products = new HashMap();
    }

    public ProductRepository(Storage<Product> productStorage, Repository<Platform> platformRepository){
        this.productStorage = productStorage;
        this.platformRepository = platformRepository;
        this.products = new HashMap();
    }
    @Override
    public void add(Product entity) throws DuplicateException, NotFoundException {
        if(products.containsKey(entity.getName())){
            throw new DuplicateException(entity);
        }
        Map<String, Platform> platforms = platformRepository.getAll();
        if(!platforms.containsKey(entity.getPlatform())){
            throw new NotFoundException(entity.getPlatform());
        }

        this.products.put(entity.getName(), entity);
        this.productStorage.add(entity);
    }

    @Override
    public Map<String, Product> getAll() {
        return products;
    }
}