package de.dhbw.pricetracker.application.repository;

import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

public class DuplicateException extends Exception {
    public DuplicateException(Platform platform){
        super("Die Platform \"" + platform.getName() + "\" existiert bereits.");
    }

    public DuplicateException(Product product){
        super("Das Produkt \"" + product.getName() + "\" existiert bereits.");
    }
}
