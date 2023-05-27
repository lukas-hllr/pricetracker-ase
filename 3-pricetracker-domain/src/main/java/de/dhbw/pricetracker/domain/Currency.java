package de.dhbw.pricetracker.domain;

public enum Currency
{
    EURO("€"),
    US_DOLLAR("$"),
    CHF("CHF"),
    YEN("¥"),
    YUAN("¥");

    final String symbol;
    private Currency(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol()
    {
        return symbol;
    }
}
