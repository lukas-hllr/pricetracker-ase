package de.dhbw.pricetracker.adapters.repository;

import java.util.Map;

public interface Repository<T> {
    //public T get() throws NotFoundException;
    public void add(T entity) throws DuplicateException, NotFoundException;

    public Map<String, T> getAll();
}
