package de.dhbw.pricetracker.adapters.htmlscraper;

import de.dhbw.pricetracker.application.htmlscraper.HtmlScraper;
import de.dhbw.pricetracker.application.htmlscraper.NoPriceFoundException;
import de.dhbw.pricetracker.adapters.network.WebClient;
import de.dhbw.pricetracker.plugins.network.CommonWebClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.StringJoiner;
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

        String[] values = selector.split("\\.");
        String element = values[0];
        StringJoiner j = new StringJoiner(" ");
        for (int i = 1; i < values.length; i++) {
            j.add(values[i]);
        }
        String clazz = j.toString();

        String price = scrape(html, element, clazz);
        return parse(price);
    }

    private static String scrape(InputStream html, String element, String clazz) throws NoPriceFoundException
    {
        Scanner htmlScanner = new Scanner(html);
        String patternString = String.format(
                "<%s\sclass=\".*?%s.*?\".*?>([0-9]{1,3}(?:[.,][0-9]{3})*(?:[.,][0-9]{2})).*?</%s>", element, clazz, element);
        Pattern p = Pattern.compile(patternString);

        if (htmlScanner.findWithinHorizon(p, 0) == null) {
            throw new NoPriceFoundException();
        }

        MatchResult match = htmlScanner.match();
        return match.group(1);
    }

    private double parse(String price)
    {
        if (price.charAt(price.length() - 3) == ',' || price.charAt(price.length() - 4) == '.') {
            price = price.replace(".", "");
            price = price.replace(",", ".");
        }
        return Double.parseDouble(price);
    }
}
