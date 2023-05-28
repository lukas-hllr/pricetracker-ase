package de.dhbw.pricetracker.application.builder;

import de.dhbw.pricetracker.application.PriceTracker;
import de.dhbw.pricetracker.application.htmlscraper.HtmlScraper;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.application.timekeeper.TimeKeeper;
import de.dhbw.pricetracker.application.ui.UserInterface;

public class PriceTrackerBuilder {
    private UserInterface ui;
    private Repository repository;
    private HtmlScraper scraper;
    private TimeKeeper timeKeeper;

    public PriceTrackerBuilder withUserInterface(UserInterface ui) {
        this.ui = ui;
        return this;
    }

    public PriceTrackerBuilder withRepository(Repository repository) {
        this.repository = repository;
        return this;
    }

    public PriceTrackerBuilder withHtmlScraper(HtmlScraper scraper) {
        this.scraper = scraper;
        return this;
    }

    public PriceTrackerBuilder withTimeKeeper(TimeKeeper timeKeeper) {
        this.timeKeeper = timeKeeper;
        return this;
    }

    public PriceTracker build() {
        return new PriceTracker(ui, repository, scraper, timeKeeper);
    }
}

