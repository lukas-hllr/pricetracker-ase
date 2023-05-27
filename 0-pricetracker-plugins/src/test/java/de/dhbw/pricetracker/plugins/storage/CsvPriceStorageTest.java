package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.domain.Currency;
import de.dhbw.pricetracker.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CsvPriceStorageTest
{

    File testCsv;
    String csvDelimiter = ";";
    Storage<Price> priceStorage;

    static Price[] testPrices = new Price[]{
            new Price("TestPrice0", 1.00, new Date(System.currentTimeMillis()), Currency.EURO),
            new Price("TestPrice1", 2.00, new Date(System.currentTimeMillis()), Currency.US_DOLLAR),
            new Price("TestPrice2", 3.00, new Date(System.currentTimeMillis()), Currency.YEN),
            new Price("TestPrice3", 4.00, new Date(System.currentTimeMillis()), Currency.YUAN),
            new Price("TestPrice4", 5.00, new Date(System.currentTimeMillis()), Currency.EURO),
    };

    static Price[] initialPrices = new Price[]{
            testPrices[0],
            testPrices[1],
            testPrices[2],
    };

    @BeforeEach
    void beforeEach(){
        testCsv = new File("src/test/resources", "price-storage-test.csv");
        deleteAndCreateNewFile(testCsv, initialPrices);
        priceStorage = new CsvPriceStorage(testCsv, csvDelimiter);
    }

    @Test
    void testInitialContents() {
        assertEquals(3, countLines(testCsv));
        assertEquals(List.of(initialPrices), priceStorage.getAll());
    }
    @Test
    void testAdd() {
        priceStorage.add(testPrices[3]);
        priceStorage.add(testPrices[4]);

        List<Price> expected = new ArrayList<>(){{
            add(testPrices[0]);
            add(testPrices[1]);
            add(testPrices[2]);
            add(testPrices[3]);
            add(testPrices[4]);
        }};

        List<Price> t = priceStorage.getAll();

        assertEquals(expected, t);
        assertEquals(5, countLines(testCsv));
    }

    @Test
    void testRemove() {
        priceStorage.remove(testPrices[1]);

        assertEquals(2, priceStorage.getAll().size());
        assertEquals(2, countLines(testCsv));
        assertFalse(priceStorage.getAll().contains(testPrices[1]));
        String csvString = priceToCsvString(testPrices[1]);
        assertFalse(fileContainsLine(testCsv, csvString));
    }

    private void deleteAndCreateNewFile(File file, Price[] prices) {
        file.delete();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        initCsvContent(file, prices);
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

    private String priceToCsvString(Price price){
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(price.product());
        joiner.add(String.format(Locale.US, "%.2f", price.value()));
        joiner.add(String.valueOf(price.timestamp().getTime()));
        joiner.add(price.currency().name());
        return joiner.toString();
    }

    private void initCsvContent(File file, Price[] prices) {
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(file, true))) {
            for (Price price: prices) {
                String csvLine = priceToCsvString(price);
                pw.println(csvLine);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}