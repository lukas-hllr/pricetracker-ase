package de.dhbw.pricetracker.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Platform(String name, String priceIdentifier)
{
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Platform that = (Platform) o;
        return Objects.equals(name, that.name) && Objects.equals(priceIdentifier, that.priceIdentifier);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, priceIdentifier);
    }
}
