package de.dhbw.pricetracker;

import de.dhbw.pricetracker.adapters.htmlscraper.RegexHtmlScraper;
import de.dhbw.pricetracker.adapters.network.WebClient;
import de.dhbw.pricetracker.adapters.repository.CommonRepository;
import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.adapters.timekeeper.ThreadedTimeKeeper;
import de.dhbw.pricetracker.application.PriceTracker;
import de.dhbw.pricetracker.application.builder.PriceTrackerBuilder;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.application.timekeeper.TimeKeeper;
import de.dhbw.pricetracker.application.ui.UserInterface;
import de.dhbw.pricetracker.application.htmlscraper.HtmlScraper;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Price;
import de.dhbw.pricetracker.domain.Product;
import de.dhbw.pricetracker.plugins.network.CommonWebClient;
import de.dhbw.pricetracker.plugins.storage.CsvPlatformStorage;
import de.dhbw.pricetracker.plugins.storage.CsvPriceStorage;
import de.dhbw.pricetracker.plugins.storage.CsvProductStorage;
import de.dhbw.pricetracker.plugins.ui.CommandLineInterface;

public class Main
{
    public static void main(String[] args)
    {
        Storage<Platform> platformStorage = new CsvPlatformStorage();
        Storage<Product> productStorage = new CsvProductStorage();
        Storage<Price> priceStorage = new CsvPriceStorage();

        WebClient webclient = new CommonWebClient();

        UserInterface ui =
                new CommandLineInterface();
        Repository repository =
                new CommonRepository(platformStorage, productStorage, priceStorage);
        HtmlScraper scraper =
                new RegexHtmlScraper(webclient);
        TimeKeeper timeKeeper =
                new ThreadedTimeKeeper();

        PriceTrackerBuilder ptBuilder = new PriceTrackerBuilder();
        ptBuilder.withHtmlScraper(scraper);
        ptBuilder.withRepository(repository);
        ptBuilder.withTimeKeeper(timeKeeper);
        ptBuilder.withUserInterface(ui);

        PriceTracker pt = ptBuilder.build();

        pt.start();
    }
}