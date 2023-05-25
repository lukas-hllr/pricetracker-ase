package de.dhbw.pricetracker.application;

import de.dhbw.pricetracker.application.htmlscraper.HtmlScraper;
import de.dhbw.pricetracker.application.repository.DuplicateException;
import de.dhbw.pricetracker.application.repository.NotFoundException;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.application.timekeeper.TimeKeeper;
import de.dhbw.pricetracker.application.ui.UIEventListener;
import de.dhbw.pricetracker.application.ui.UserInterface;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

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
        ui.setUIEventListener(this);
        ui.start();
    }

    @Override
    public void onAddPlatformEvent(Platform platform) {
        try {
            platformRepository.add(platform);
        } catch (DuplicateException e) {
            ui.error(e.getMessage());
        } catch (NotFoundException e) {
            ui.error(e.getMessage());
        }
    }

    @Override
    public void onAddProductEvent(Product product) {

    }
}
