package de.dhbw.pricetracker.application.repository;

import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

public class NotFoundException extends Exception
{
    public NotFoundException(Platform platform)
    {
        super("Die Platform \"" + platform.getName() + "\" existiert nicht.");
    }

    public NotFoundException(Class c, String name)
    {
        super(
                c == Platform.class ?
                        "Die Platform \"" + name + "\" existiert nicht." :
                        "Das Product \"" + name + "\" existiert nicht."
        );
    }

    public NotFoundException(Product product)
    {
        super("Das Produkt \"" + product + "\" existiert nicht.");
    }


}
