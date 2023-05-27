package de.dhbw.pricetracker.adapters.htmlscraper;

import de.dhbw.pricetracker.application.network.WebClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WebClientMock implements WebClient {
    @Override
    public InputStream getHtml(String url) throws IOException {
        if(url.equals("amazon")) {
            String path = Thread.currentThread().getContextClassLoader().getResource("amazon.html").getPath();
            File f = new File(path);
            return new FileInputStream(f);
        } else if(url.equals("otto")){
            String path = Thread.currentThread().getContextClassLoader().getResource("otto.html").getPath();
            File f = new File(path);
            return new FileInputStream(f);
        }
        return InputStream.nullInputStream();
    }
}
