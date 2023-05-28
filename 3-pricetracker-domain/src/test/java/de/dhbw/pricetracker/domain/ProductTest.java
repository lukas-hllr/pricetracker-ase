package de.dhbw.pricetracker.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest
{
    @Test
    void testName(){
        Product p = new Product("Produkt", "Plattform", "URL", Currency.EURO);

        assertEquals("Produkt", p.name());
    }

}