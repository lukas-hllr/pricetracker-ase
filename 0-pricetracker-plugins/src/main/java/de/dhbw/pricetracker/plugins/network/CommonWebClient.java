package de.dhbw.pricetracker.plugins.network;


import de.dhbw.pricetracker.adapters.network.WebClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class CommonWebClient implements WebClient
{
    @Override
    public InputStream getHtml(String url) throws IOException {
        URL urlObj = new URL(url);

        URLConnection hc = urlObj.openConnection();
        hc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        return hc.getInputStream();
    }
}
