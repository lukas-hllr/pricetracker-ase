package de.dhbw.pricetracker;

import de.dhbw.pricetracker.adapters.htmlscraper.RegexHtmlScraper;
import de.dhbw.pricetracker.adapters.repository.CommonRepository;
import de.dhbw.pricetracker.adapters.timekeeper.ThreadedTimeKeeper;
import de.dhbw.pricetracker.application.PriceTracker;
import de.dhbw.pricetracker.application.repository.Repository;
import de.dhbw.pricetracker.application.timekeeper.TimeKeeper;
import de.dhbw.pricetracker.application.ui.UserInterface;
import de.dhbw.pricetracker.application.htmlscraper.HtmlScraper;
import de.dhbw.pricetracker.plugins.ui.CommandLineInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface ui =
                new CommandLineInterface();
        Repository repository =
                new CommonRepository();
        HtmlScraper scraper =
                new RegexHtmlScraper();
        TimeKeeper timeKeeper =
                new ThreadedTimeKeeper();

        PriceTracker pt = new PriceTracker(
                ui,
                repository,
                scraper,
                timeKeeper
        );

        pt.start();
    }
}