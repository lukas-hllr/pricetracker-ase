package de.dhbw.pricetracker.application.ui;

import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

public interface UserInterface {
    public void start();
    public void setUIEventListener(UIEventListener listener);
    public void helpForAddPlatformEvent();
    public void addPlatformEvent();
    public void helpForAddProductEvent();
    public void addProductEvent();
    public void info(String message);
    public void success(String message);
    public void error(String message);
    public void warning(String message);
}
