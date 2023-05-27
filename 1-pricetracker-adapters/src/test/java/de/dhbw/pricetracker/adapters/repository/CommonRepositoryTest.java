package de.dhbw.pricetracker.adapters.repository;

import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.application.repository.DuplicateException;
import de.dhbw.pricetracker.application.repository.NotFoundException;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.domain.*;
import de.dhbw.pricetracker.plugins.storage.CsvPlatformStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommonRepositoryTest {

    Repository repository;

    static Platform[] testPlatforms = new Platform[]{
            new CommonPlatform("TestPlatform0", "TestSelector0"),
            new CommonPlatform("TestPlatform1", "TestSelector1"),
            new CommonPlatform("TestPlatform2", "TestSelector2"),
            new CommonPlatform("TestPlatform3", "TestSelector3"),
    };

    static Product[] testProducts = new Product[]{
            new CommonProduct("TestProduct0", "TestPlatform0", "url0", Currency.EURO),
            new CommonProduct("TestProduct1", "TestPlatform0", "url1", Currency.US_DOLLAR),
            new CommonProduct("TestProduct2", "TestPlatform1", "url2", Currency.YEN),
            new CommonProduct("TestProduct3", "TestPlatform1", "url3", Currency.YUAN),
            new CommonProduct("TestProduct4", "TestPlatform2", "url4", Currency.EURO),
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

        repository = new CommonRepository(platformStorage, productStorage);
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
    void removePlatform() throws NotFoundException {
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