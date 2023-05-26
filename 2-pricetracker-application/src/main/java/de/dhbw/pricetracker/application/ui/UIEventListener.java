package de.dhbw.pricetracker.application.ui;

import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

public interface UIEventListener
{
    public void onAddPlatformEvent(Platform platform);
    public void onAddProductEvent(Product product);

    public void onUpdatePriceEvent();
    //public void onPriceChangeEvent(double oldPrice, Product product);
}
