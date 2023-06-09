package de.dhbw.pricetracker.adapters.repository;

import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.application.repository.DuplicateException;
import de.dhbw.pricetracker.application.repository.NotFoundException;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommonRepositoryTest {

    Repository repository;

    static Platform[] testPlatforms = new Platform[]{
            new Platform("TestPlatform0", "TestSelector0"),
            new Platform("TestPlatform1", "TestSelector1"),
            new Platform("TestPlatform2", "TestSelector2"),
            new Platform("TestPlatform3", "TestSelector3"),
    };

    static Product[] testProducts = new Product[]{
            new Product("TestProduct0", "TestPlatform0", "url0", Currency.EURO),
            new Product("TestProduct1", "TestPlatform0", "url1", Currency.US_DOLLAR),
            new Product("TestProduct2", "TestPlatform1", "url2", Currency.YEN),
            new Product("TestProduct3", "TestPlatform1", "url3", Currency.YUAN),
            new Product("TestProduct4", "TestPlatform2", "url4", Currency.EURO),
    };

    static Price[] testPrices = new Price[]{
            new Price(testProducts[0].name(),1.99, new Date(1685263671873L), Currency.EURO),
            new Price(testProducts[0].name(),3.49, new Date(1685963671873L), Currency.EURO),
            new Price(testProducts[0].name(),0.99, new Date(1686963671873L), Currency.EURO),
            new Price(testProducts[1].name(),299.90, new Date(1683163671873L), Currency.US_DOLLAR),
            new Price(testProducts[3].name(),3, new Date(1683163671873L), Currency.YUAN),
    };
    @BeforeEach
    void setUp() {
        Storage<Platform> platformStorage = new StorageMock<>();
        platformStorage.add(testPlatforms[0]);
        platformStorage.add(testPlatforms[1]);

        Storage<Product> productStorage = new StorageMock<>();
        productStorage.add(testProducts[0]);
        productStorage.add(testProducts[1]);
        productStorage.add(testProducts[2]);

        Storage<Price> priceStorage = new StorageMock<>();
        priceStorage.add(testPrices[0]);
        priceStorage.add(testPrices[1]);
        priceStorage.add(testPrices[2]);

        repository = new CommonRepository(platformStorage, productStorage, priceStorage);
    }

    @Test
    void testGetAllPlatforms() {
        List<Platform> expected = List.of(new Platform[]{
                testPlatforms[0],
                testPlatforms[1],
        });
        List<Platform> result = repository.getAllPlatforms();

        assertTrue(result.containsAll(expected));
        assertEquals(2 , result.size());
    }

    @Test
    void testGetAllProducts() {
        List<Product> expected = List.of(new Product[]{
                testProducts[0],
                testProducts[1],
                testProducts[2],
        });
        List<Product> result = repository.getAllProducts();

        assertTrue(result.containsAll(expected));
        assertEquals(3, result.size());
    }

    @Test
    void testAddPlatform() throws DuplicateException {
        repository.addPlatform(testPlatforms[2]);
        List<Platform> result = repository.getAllPlatforms();

        assertTrue(result.contains(testPlatforms[2]));
        assertEquals(3, result.size());
    }

    @Test
    void testAddingDuplicatePlatformThrowsError() {
        assertThrows(DuplicateException.class, () -> repository.addPlatform(testPlatforms[0]));
    }

    @Test
    void testRemovePlatform() throws NotFoundException {
        repository.removePlatform(testPlatforms[1]);
        List<Platform> resultPlatforms = repository.getAllPlatforms();
        List<Product> resultProducts = repository.getAllProducts();

        assertFalse(resultPlatforms.contains(testPlatforms[1]));
        assertEquals(1, resultPlatforms.size());

        assertFalse(resultProducts.contains(testProducts[2]));
        assertEquals(1, resultPlatforms.size());
    }

    @Test
    void testRemovingUnknownPlatformThrowsError() {
        assertThrows(NotFoundException.class, () -> repository.removePlatform(testPlatforms[3]));
    }

    @Test
    void testAddProduct() throws DuplicateException, NotFoundException {
        repository.addProduct(testProducts[3]);
        List<Product> result = repository.getAllProducts();

        assertFalse(result.contains(testProducts[4]));
        assertEquals(4, result.size());
    }

    @Test
    void testAddingDuplicateProductThrowsError() {
        assertThrows(DuplicateException.class, () -> repository.addProduct(testProducts[0]));
    }

    @Test
    void testAddingProductWithUnknownPlatformThrowsError() {
        assertThrows(NotFoundException.class, () -> repository.addProduct(testProducts[4]));
    }

    @Test
    void testRemoveProduct() throws NotFoundException {
        repository.removeProduct(testProducts[0]);
        List<Product> result = repository.getAllProducts();

        assertFalse(result.contains(testProducts[0]));
        assertEquals(2, result.size());
    }

    @Test
    void testRemovingUnknownProductThrowsError() {
        assertThrows(NotFoundException.class, () -> repository.removeProduct(testProducts[4]));
    }
}