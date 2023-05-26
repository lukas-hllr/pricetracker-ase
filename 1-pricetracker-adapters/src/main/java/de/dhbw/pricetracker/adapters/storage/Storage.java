package de.dhbw.pricetracker.adapters.storage;

import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

import java.util.List;

public interface Storage<T> {
    public List<T> getAll();
    public void add(T entity);
    public void remove(T entity);

}
