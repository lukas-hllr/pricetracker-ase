package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.domain.CommonProduct;
import de.dhbw.pricetracker.domain.Product;

import java.io.*;
import java.util.*;

public class CsvProductStorage implements Storage<Product> {
    private String csvDelimiter;
    private File productCsv;
    private List<Product> products;


    public CsvProductStorage(){
        String directory = System.getProperty("user.home");
        String filename = "pricetracker_product.csv";

        this.products = new ArrayList<>();
        this.productCsv = new File(directory, filename);
        this.csvDelimiter = ";";
        createFileIfMissing(productCsv);
        readProductsFromCsv();
    }
    public CsvProductStorage(File csvFile, String csvDelimiter){
        this.products = new ArrayList();
        this.productCsv = csvFile;
        this.csvDelimiter = csvDelimiter;
        createFileIfMissing(productCsv);
        readProductsFromCsv();
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
                products.add(newProduct);
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
    private String productToCsvString(Product product){
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(product.getName());
        joiner.add(product.getPlatform());
        joiner.add(product.getURL());
        joiner.add(String.valueOf(product.getPrice()));
        return joiner.toString();
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public void add(Product entity) {
        String csvLine = productToCsvString(entity);
        appendToFile(productCsv, csvLine);
    }

    @Override
    public void remove(Product entity) {
        productCsv.delete();
        createFileIfMissing(productCsv);
        for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
            Product product = iterator.next();
            if(product.equals(entity)){
                iterator.remove();
            } else {
                String csvLine = productToCsvString(entity);
                appendToFile(productCsv, csvLine);
            }
        }
    }
}
