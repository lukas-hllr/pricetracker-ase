package de.dhbw.pricetracker.adapters.repository;

import de.dhbw.pricetracker.application.repository.DuplicateException;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.domain.Platform;

import java.util.HashMap;
import java.util.Map;

public class PlatformRepository implements Repository<Platform> {

    private Storage storage;
    private Map<String, Platform> platforms;

    public PlatformRepository(Storage storage){
        this.storage = storage;
        this.platforms = new HashMap();
    }
    @Override
    public void add(Platform entity) throws DuplicateException {
        if(platforms.containsKey(entity.getName())){
            throw new DuplicateException(entity);
        }

        this.platforms.put(entity.getName(), entity);
    }

    @Override
    public Map<String, Platform> getAll() {
        return platforms;
    }
}
