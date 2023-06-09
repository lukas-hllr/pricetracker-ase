package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.domain.Currency;
import de.dhbw.pricetracker.domain.Product;

import java.io.File;
import java.util.StringJoiner;

public class CsvProductStorage extends CsvStorage<Product>
{

    public CsvProductStorage()
    {
        super(new File(System.getProperty("user.home"), "pricetracker_products.csv"), ";");

    }

    public CsvProductStorage(File csvFile, String csvDelimiter)
    {
        super(csvFile, csvDelimiter);
    }

    @Override
    String entityToCsvString(Product entity)
    {
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(entity.name());
        joiner.add(entity.platform());
        joiner.add(entity.url());
        joiner.add(entity.currency().name());
        return joiner.toString();
    }

    @Override
    Product csvStringToEntity(String csvLine)
    {
        String[] values = csvLine.split(this.csvDelimiter);

        String name = values[0];
        String platform = values[1];
        String url = values[2];
        Currency currency = Currency.valueOf(values[3]);

        return new Product(name, platform, url, currency);
    }
}
