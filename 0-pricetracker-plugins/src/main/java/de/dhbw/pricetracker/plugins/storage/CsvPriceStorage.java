package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.domain.CommonProduct;
import de.dhbw.pricetracker.domain.Currency;
import de.dhbw.pricetracker.domain.Price;
import de.dhbw.pricetracker.domain.Product;

import java.io.File;
import java.util.Date;
import java.util.Locale;
import java.util.StringJoiner;

public class CsvPriceStorage extends CsvStorage<Price> {

    public CsvPriceStorage(){
        super(new File(System.getProperty("user.home"),"pricetracker_prices.csv"), ";");
    }

    public CsvPriceStorage(File csvFile, String csvDelimiter){
        super(csvFile, csvDelimiter);
    }

    @Override
    String entityToCsvString(Price entity) {
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(entity.product());
        joiner.add(String.format(Locale.US, "%.2f", entity.value()));
        joiner.add(String.valueOf(entity.timestamp().getTime()));
        joiner.add(entity.currency().name());
        return joiner.toString();
    }

    @Override
    Price csvStringToEntity(String csvLine) {
        String[] values = csvLine.split(this.csvDelimiter);

        String product = values[0];
        double value = Double.parseDouble(values[1]);
        Date timestamp = new Date(Long.parseLong(values[2]));
        Currency currency = Currency.valueOf(values[3]);

        return new Price(product, value, timestamp, currency);
    }
}
