package de.dhbw.pricetracker.domain;

import java.util.Date;
import java.util.Locale;

public record Price(String product, double value, Date timestamp, Currency currency)
{
    public Price(String product, double value, Date timestamp, Currency currency)
    {
        this.product = product;
        if (value >= 0.0) {
            this.value = value;
        } else {
            this.value = 0.0;
        }
        this.timestamp = timestamp;
        this.currency = currency;
    }

    public Price(String product, double value, Currency currency)
    {
        this(product, value, new Date(System.currentTimeMillis()), currency);
    }

    public Price(String product, Currency currency)
    {
        this(product, 0.0, currency);
    }

    @Override
    public String toString()
    {
        return String.format(Locale.US, "%.2f %s", value, currency.symbol);
    }
}
