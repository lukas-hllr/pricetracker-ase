package de.dhbw.pricetracker.adapters.htmlscraper;

import de.dhbw.pricetracker.application.htmlscraper.HtmlScraper;
import de.dhbw.pricetracker.application.htmlscraper.NoPriceFoundException;
import de.dhbw.pricetracker.application.network.WebClient;
import de.dhbw.pricetracker.plugins.network.CommonWebClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class RegexHtmlScraper implements HtmlScraper
{

    private WebClient client;

    public RegexHtmlScraper()
    {
        this.client = new CommonWebClient();
    }
    public RegexHtmlScraper(WebClient client)
    {
        this.client = client;
    }

    @Override
    public double scrapePrice(String url, String selector) throws IOException, NoPriceFoundException
    {
        InputStream html = client.getHtml(url);
        String price = scrape(html);

        return Double.valueOf(price);
    }

    private static String scrape(InputStream html) throws NoPriceFoundException
    {
        Scanner htmlScanner = new Scanner(html);
        Pattern p = Pattern.compile(
                "<span\sclass=\".*?priceToPay.*?\".*?>([0-9]{1,3}(?:[.,][0-9]{3})*(?:[.,][0-9]{2})).*?</span>"
        );

        if (htmlScanner.findWithinHorizon(p, 0) == null)
        {
            throw new NoPriceFoundException();
        }

        MatchResult match = htmlScanner.match();
        String price = match.group(1);
        return price;
    }
}