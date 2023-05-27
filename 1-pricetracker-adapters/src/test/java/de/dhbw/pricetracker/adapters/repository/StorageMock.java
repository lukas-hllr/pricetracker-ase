package de.dhbw.pricetracker.adapters.repository;

import de.dhbw.pricetracker.adapters.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class StorageMock<T> implements Storage<T> {

    private List<T> storage;

    public StorageMock(){
        storage = new ArrayList<>();
    }
    @Override
    public List<T> getAll() {
        return storage;
    }

    @Override
    public void add(T entity) {
        storage.add(entity);
    }

    @Override
    public void remove(T entity) {
        storage.remove(entity);
    }
}
