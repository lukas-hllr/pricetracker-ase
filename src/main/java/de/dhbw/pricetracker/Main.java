package de.dhbw.pricetracker;

import de.dhbw.pricetracker.adapters.htmlscraper.RegexHtmlScraper;
import de.dhbw.pricetracker.adapters.repository.PlatformRepository;
import de.dhbw.pricetracker.adapters.repository.ProductRepository;
import de.dhbw.pricetracker.adapters.timekeeper.ThreadedTimeKeeper;
import de.dhbw.pricetracker.application.PriceTracker;
import de.dhbw.pricetracker.application.timekeeper.TimeKeeper;
import de.dhbw.pricetracker.application.ui.UserInterface;
import de.dhbw.pricetracker.application.htmlscraper.HtmlScraper;
import de.dhbw.pricetracker.application.repository.DuplicateException;
import de.dhbw.pricetracker.application.repository.NotFoundException;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.domain.CommonPlatform;
import de.dhbw.pricetracker.domain.CommonProduct;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;
import de.dhbw.pricetracker.plugins.ui.CommandLineInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface ui =
                new CommandLineInterface();
        Repository<Platform> platformRepository =
                new PlatformRepository();
        Repository<Product> productRepository =
                new ProductRepository(platformRepository);
        HtmlScraper scraper =
                new RegexHtmlScraper();
        TimeKeeper timeKeeper =
                new ThreadedTimeKeeper();

        PriceTracker pt = new PriceTracker(
                ui,
                platformRepository,
                productRepository,
                scraper,
                timeKeeper
        );

        pt.start();
    }
}