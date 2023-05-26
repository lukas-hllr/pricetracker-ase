package de.dhbw.pricetracker.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommonPlatform implements Platform {
    private String name;
    private String priceIdentifier;

    public CommonPlatform(String name, String priceIdentifier) {
        this.name = name;
        this.priceIdentifier = priceIdentifier;
    }

    public CommonPlatform(String name, String priceIdentifier, List<Product> products) {
        this.name = name;
        this.priceIdentifier = priceIdentifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPriceSelector() {
        return this.priceIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonPlatform that = (CommonPlatform) o;
        return Objects.equals(name, that.name) && Objects.equals(priceIdentifier, that.priceIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priceIdentifier);
    }
}
