package de.dhbw.pricetracker.application.network;

import java.io.IOException;
import java.io.InputStream;

public interface WebClient {
    public InputStream getHtml(String url) throws IOException;
}
