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
    private final UserInterface ui;
    private final Repository repository;
    private final HtmlScraper scraper;
    private final TimeKeeper timeKeeper;

    public PriceTracker(
            UserInterface ui,
            Repository repository,
            HtmlScraper scraper,
            TimeKeeper timeKeeper
    ) {
        this.ui = ui;
        this.repository = repository;
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
            repository.addPlatform(platform);
            ui.onSuccess();
        } catch (DuplicateException e) {
            ui.onError(e);
        }
    }

    @Override
    public void onRemovePlatformEvent(Platform platform) {
        try {
            repository.removePlatform(platform);
            ui.onSuccess();
        } catch (NotFoundException e) {
            ui.onError(e);
        }
    }

    @Override
    public void onAddProductEvent(Product product) {
        try {
            repository.addProduct(product);
            ui.onSuccess();
        } catch (DuplicateException | NotFoundException e) {
            ui.onError(e);
        }
    }

    @Override
    public void onRemoveProductEvent(Product product) {
        try {
            repository.removeProduct(product);
            ui.onSuccess();
        } catch (NotFoundException e) {
            ui.onError(e);
        }
    }

    @Override
    public void onUpdatePriceEvent() {
        ui.onUpdateStartedEvent();
        for (Product product : repository.getAllProducts()) {
            ui.onUpdateStartedEvent(product);
            try {
                double newPrice = scraper.scrapePrice(product.getURL(), "");
                this.handleNewPrice(newPrice, product);
            } catch (IOException | NoPriceFoundException e) {
                ui.onError(e);
            }
        }
    }

    @Override
    public void onListProductsEvent() {
        ui.listProductsEvent(repository.getAllProducts());
    }

    @Override
    public void onListPlatformsEvent() {
        ui.listPlatformsEvent(repository.getAllPlatforms());
    }

    private void handleNewPrice(double newPrice, Product product) {
        double oldPrice = product.getPrice();
        if(newPrice > oldPrice){
            ui.onPriceIncreased(newPrice, product);
        } else if(newPrice < oldPrice){
            ui.onPriceDecreased(newPrice, product);
        } else {
            ui.onNoPriceChange(product);
        }
        product.setPrice(newPrice);
    }
}
