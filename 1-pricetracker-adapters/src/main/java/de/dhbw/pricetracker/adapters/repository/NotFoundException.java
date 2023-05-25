package de.dhbw.pricetracker.adapters.repository;

import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

public class NotFoundException extends Exception {
    public NotFoundException(Platform platform){
        super("Die Platform \"" + platform.getName() + "\" existiert nicht.");
    }
    public NotFoundException(String platform){
        super("Die Platform \"" + platform + "\" existiert nicht.");
    }
    public NotFoundException(Product product){
        super("Das Produkt \"" + product + "\" existiert nicht.");
    }


}
