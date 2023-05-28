package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.domain.Product;
import de.dhbw.pricetracker.domain.Currency;
import de.dhbw.pricetracker.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CsvProductStorageTest {

    File testCsv;
    String csvDelimiter = ";";
    Storage<Product> productStorage;

    static Product[] testProducts = new Product[]{
            new Product("TestProduct0", "TestPlatform0", "url0", Currency.EURO),
            new Product("TestProduct1", "TestPlatform0", "url1", Currency.US_DOLLAR),
            new Product("TestProduct2", "TestPlatform1", "url2", Currency.YEN),
            new Product("TestProduct3", "TestPlatform1", "url3", Currency.YUAN),
            new Product("TestProduct4", "TestPlatform2", "url4", Currency.EURO),
    };

    static Product[] initialProducts = new Product[]{
            new Product("TestProduct0", "TestPlatform0", "url0", Currency.EURO),
            new Product("TestProduct1", "TestPlatform0", "url1", Currency.US_DOLLAR),
            new Product("TestProduct2", "TestPlatform1", "url2", Currency.YEN),
    };

    @BeforeEach
    void beforeEach(){
        testCsv = new File("src/test/resources", "product-storage-test.csv");
        deleteAndCreateNewFile(testCsv, initialProducts);
        productStorage = new CsvProductStorage(testCsv, csvDelimiter);
    }

    @Test
    void testInitialContents() {
        assertEquals(3, countLines(testCsv));
        assertEquals(List.of(initialProducts), productStorage.getAll());
    }
    @Test
    void testAdd() {
        productStorage.add(testProducts[3]);
        productStorage.add(testProducts[4]);

        List<Product> expected = new ArrayList<>(){{
            add(testProducts[0]);
            add(testProducts[1]);
            add(testProducts[2]);
            add(testProducts[3]);
            add(testProducts[4]);
        }};

        List<Product> t = productStorage.getAll();

        assertEquals(expected, t);
        assertEquals(5, countLines(testCsv));
    }

    @Test
    void testRemove() {
        productStorage.remove(testProducts[1]);

        assertEquals(2, productStorage.getAll().size());
        assertEquals(2, countLines(testCsv));
        assertFalse(productStorage.getAll().contains(testProducts[1]));
        String csvString = productToCsvString(testProducts[1]);
        assertFalse(fileContainsLine(testCsv, csvString));
    }

    private void deleteAndCreateNewFile(File file, Product[] products) {
        file.delete();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        initCsvContent(file, products);
    }

    private boolean fileContainsLine(File file, String line){
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if(currentLine.equals(line)) {
                    return true;
                }
            }
        } catch(FileNotFoundException ignore) {}
        return false;
    }

    private int countLines(File file){
        int lines = 0;
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                lines++;
                scanner.nextLine();
            }
        } catch(FileNotFoundException ignore) {}
        return lines;
    }

    private String productToCsvString(Product product){
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(product.name());
        joiner.add(product.platform());
        joiner.add(product.url());
        joiner.add(product.currency().name());
        return joiner.toString();
    }

    private void initCsvContent(File file, Product[] products) {
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(file, true))) {
            for (Product product: products) {
                String csvLine = productToCsvString(product);
                pw.println(csvLine);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}