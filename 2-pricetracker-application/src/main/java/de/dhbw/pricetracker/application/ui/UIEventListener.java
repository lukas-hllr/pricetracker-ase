package de.dhbw.pricetracker.application.ui;

import de.dhbw.pricetracker.application.repository.NotFoundException;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

import java.util.Map;

public interface UIEventListener
{
    public void onAddPlatformEvent(Platform platform);
    public void onRemovePlatformEvent(Platform platform);
    public void onAddProductEvent(Product product);
    public void onRemoveProductEvent(Product product);
    public void onUpdatePriceEvent();
    public void onListProductsEvent();
    public void onListPlatformsEvent();
    public void onListCurrenciesEvent();
    public void onListPricesRequestedEvent();
    public void onListPricesEvent(Product product);
}
