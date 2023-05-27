package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.domain.CommonProduct;
import de.dhbw.pricetracker.domain.Product;

import java.io.File;
import java.util.StringJoiner;

public class CsvProductStorage extends CsvStorage<Product> {

    public CsvProductStorage(){
        super();
    }

    public CsvProductStorage(File csvFile, String csvDelimiter){
        super(csvFile, csvDelimiter);
    }

    @Override
    String entityToCsvString(Product entity) {
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(entity.getName());
        joiner.add(entity.getPlatform());
        joiner.add(entity.getURL());
        joiner.add(String.valueOf(entity.getPrice()));
        return joiner.toString();
    }

    @Override
    Product csvStringToEntity(String csvLine) {
        String[] values = csvLine.split(this.csvDelimiter);

        String name = values[0];
        String platform = values[1];
        String url = values[2];
        double price = Double.parseDouble(values[3]);

        return new CommonProduct(name, platform, url, price);
    }
}
