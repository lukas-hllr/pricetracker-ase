package de.dhbw.pricetracker.adapters.htmlscraper;

import de.dhbw.pricetracker.application.htmlscraper.HtmlScraper;
import de.dhbw.pricetracker.application.htmlscraper.NoPriceFoundException;
import de.dhbw.pricetracker.adapters.network.WebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RegexHtmlScraperTest {

    HtmlScraper scraper;
    @BeforeEach
    void setUp() {
        WebClient mockWebClient = new WebClientMock();
        scraper = new RegexHtmlScraper(mockWebClient);
    }

    @Test
    void testScrapePriceAmazon() throws IOException, NoPriceFoundException {
        double expectedPrice = 7.99;
        double price = scraper.scrapePrice("amazon", "span.priceToPay");
        assertEquals(expectedPrice, price);
    }

    @Test
    void testScrapePriceEmpty() {
        assertThrows(NoPriceFoundException.class, () -> scraper.scrapePrice("otto", "span.priceToPay"));
    }
}