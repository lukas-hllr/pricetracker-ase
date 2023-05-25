package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.domain.CommonPlatform;
import de.dhbw.pricetracker.domain.CommonProduct;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class CsvStorage implements Storage {
    public static final String csvDelimiter = ";";

    private File platformCsv;
    private File productCsv;
    private Map<String, Platform> platforms;
    private Map<String, Product> products;


    public CsvStorage(){
        String userHome = System.getProperty("user.home");

        initPlatforms(userHome);

        initProducts(userHome);
    }

    private void initProducts(String csvDirectory) {
        this.products = new HashMap();
        productCsv = new File(csvDirectory, "pricetracker_product.csv");
        createFileIfMissing(productCsv);
        readProductsFromCsv();
    }

    private void initPlatforms(String csvDirectory) {
        this.platforms = new HashMap();
        platformCsv = new File(csvDirectory, "pricetracker_platform.csv");
        createFileIfMissing(platformCsv);
        readPlatformsFromCsv();
    }

    private void readProductsFromCsv() {
        try (BufferedReader productReader = new BufferedReader(new FileReader(productCsv))){
            String line;
            while ((line = productReader.readLine()) != null) {
                String[] values = line.split(csvDelimiter);

                String name = values[0];
                String platform = values[1];
                String url = values[2];
                double price = Double.valueOf(values[3]);

                Product newProduct = new CommonProduct(name, platform, url, price);
                products.put(values[0], newProduct);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readPlatformsFromCsv() {
        try (BufferedReader platformReader = new BufferedReader(new FileReader(platformCsv))){
            String line;
            while ((line = platformReader.readLine()) != null) {
                String[] values = line.split(csvDelimiter);

                String name = values[0];
                String priceIdentifier = values[1];

                Platform newPlatform = new CommonPlatform(name, priceIdentifier);
                platforms.put(values[0], newPlatform);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFileIfMissing(File file) {
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void appendToFile(File file, String string) {
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(file, true))) {
            pw.println(string);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String platformToCsvString(Platform platform){
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(platform.getName());
        joiner.add(platform.getPriceSelector());
        return joiner.toString();
    }
    private String productToCsvString(Product product){
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(product.getName());
        joiner.add(product.getPlatform());
        joiner.add(product.getURL());
        joiner.add(String.valueOf(product.getPrice()));
        return joiner.toString();
    }

    @Override
    public void addPlatform(Platform platform){
        String csvLine = platformToCsvString(platform);
        appendToFile(platformCsv, csvLine);
    }

    @Override
    public void addProduct(Product product) {
        String csvLine = productToCsvString(product);
        appendToFile(productCsv, csvLine);
    }
}
