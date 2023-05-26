package de.dhbw.pricetracker.application;

import de.dhbw.pricetracker.application.htmlscraper.HtmlScraper;
import de.dhbw.pricetracker.application.htmlscraper.NoPriceFoundException;
import de.dhbw.pricetracker.application.repository.DuplicateException;
import de.dhbw.pricetracker.application.repository.NotFoundException;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.application.timekeeper.TimeKeeper;
import de.dhbw.pricetracker.application.ui.UIEventListener;
import de.dhbw.pricetracker.application.ui.UserInterface;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

import java.io.IOException;
import java.util.Map;

public class PriceTracker implements UIEventListener
{
    private UserInterface ui;
    private Repository<Platform> platformRepository;
    private Repository<Product> productRepository;
    private HtmlScraper scraper;
    private TimeKeeper timeKeeper;

    public PriceTracker(
            UserInterface ui,
            Repository<Platform> platformRepository,
            Repository<Product> productRepository,
            HtmlScraper scraper,
            TimeKeeper timeKeeper
    ) {
        this.ui = ui;
        this.platformRepository = platformRepository;
        this.productRepository = productRepository;
        this.scraper = scraper;
        this.timeKeeper = timeKeeper;
    }

    public void start()
    {
        timeKeeper.setUIEventListener(this);
        ui.setUIEventListener(this);
        timeKeeper.start();
        ui.start();
    }

    @Override
    public void onAddPlatformEvent(Platform platform) {
        try {
            platformRepository.add(platform);
        } catch (DuplicateException | NotFoundException e) {
            ui.error(e.getMessage());
        }
    }

    @Override
    public void onAddProductEvent(Product product) {
        try {
            productRepository.add(product);
        } catch (DuplicateException | NotFoundException e) {
            ui.error(e.getMessage());
        }
    }

    @Override
    public void onUpdatePriceEvent() {
        ui.info("Update started.");
        for (Map.Entry<String, Product> entry : productRepository.getAll().entrySet()) {
            Product product = entry.getValue();
            ui.info("Fetching price of \"" + product.getName() + "\"");
            try {
                double newPrice = scraper.scrapePrice(product.getURL(), "");
                System.out.println(newPrice);
                ui.info("New Price: " + newPrice);
            } catch (IOException | NoPriceFoundException e) {
                ui.error(e.getMessage());
            }
        }
    }
}
