package de.dhbw.pricetracker.application.htmlscraper;

import java.io.IOException;

public interface HtmlScraper
{
    public double scrapePrice(String url, String selector) throws IOException, NoPriceFoundException;
}

