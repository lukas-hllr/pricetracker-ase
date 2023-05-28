package de.dhbw.pricetracker.plugins.ui;

import de.dhbw.pricetracker.plugins.ui.console.MessageType;
import de.dhbw.pricetracker.application.ui.UIEventListener;
import de.dhbw.pricetracker.application.ui.UserInterface;
import de.dhbw.pricetracker.domain.*;
import de.dhbw.pricetracker.plugins.ui.console.Console;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommandLineInterface implements UserInterface
{

    UIEventListener listener;

    List<Platform> platformContext;
    List<Product> productContext;
    List<Currency> currencyContext;

    @Override
    public void start()
    {
        Console.println(MessageType.SUCCESS, "Willkommen beim PriceTracker!");
        loop:
        while (true) {
            String input = Console.read().toLowerCase();

            switch (input) {
                case "platforms add":
                    addPlatformEvent();
                    break;
                case "platforms remove":
                    removePlatformEvent();
                    break;
                case "platforms list":
                    listener.onListPlatformsEvent();
                    break;
                case "products add":
                    addProductEvent();
                    break;
                case "products remove":
                    removeProductEvent();
                    break;
                case "products list":
                    listener.onListProductsEvent();
                    break;
                case "prices list":
                    listener.onListPricesEvent();
                    break;
                case "platforms add --help":
                    helpForAddPlatformEvent();
                    break;
                case "products add --help":
                    helpForAddProductEvent();
                    break;
                case "exit":
                    break loop;
                default:
                    inputError(input);
                    break;
            }
        }
    }

    @Override
    public void setUIEventListener(UIEventListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void helpForAddPlatformEvent()
    {
        Console.println("Um dem PriceTracker eine Platform hinzuzufügen, benötigst du folgenden Daten:");
        Console.print(MessageType.REQUEST, "Name: ");
        Console.println("Ein selbst gewählter Name.");
        Console.print(MessageType.REQUEST, "Preisselektor: ");
        Console.println("Ein CSS-Selektor, welcher den Preis der Produkte der Platform eindeutig bestimmt. Das System unterstützt Element- und Klassenselektoren. Beispiel: \"span.priceToPay\"");
    }

    @Override
    public void addPlatformEvent()
    {
        String name = Console.read("Name: ");
        String priceIdentifier = Console.read("Preisselektor: ");

        listener.onAddPlatformEvent(new CommonPlatform(name, priceIdentifier));
    }

    @Override
    public void removePlatformEvent()
    {
        Console.println(MessageType.WARN, "Achtung: Alle dazugehörigen Produkte werden ebenfalls gelöscht!");
        listener.onListPlatformsEvent();
        String input = Console.read("Welche Platform?: ");
        try {
            int index = Integer.parseInt(input);
            Platform platform = platformContext.get(index);
            listener.onRemovePlatformEvent(platform);
        } catch (NumberFormatException e) {
            inputError(input);
        }
    }

    @Override
    public void helpForAddProductEvent()
    {
        Console.println("Um dem PriceTracker ein Produkt hinzuzufügen, benötigst du folgenden Daten:");
        Console.print(MessageType.REQUEST, "Name: ");
        Console.println("Ein selbst gewählter Name.");
        Console.print(MessageType.REQUEST, "Platform: ");
        Console.println("Name der Platform, auf welcher sich diese Produkt befindet.");
        Console.print(MessageType.REQUEST, "URL: ");
        Console.println("URL des Produkts.");
    }

    @Override
    public void addProductEvent()
    {
        String name = Console.read("Name: ");
        String platform = Console.read("Platform: ");
        String url = Console.read("URL: ");
        listener.onListCurrenciesEvent();
        String currencyInput = Console.read("Währung: ");
        try {
            int index = Integer.parseInt(currencyInput);
            Currency currency = currencyContext.get(index);
            Product product = new CommonProduct(name, platform, url, currency);
            listener.onAddProductEvent(product);
        } catch (NumberFormatException e) {
            inputError(currencyInput);
        }


    }

    @Override
    public void removeProductEvent()
    {
        listener.onListProductsEvent();
        String input = Console.read("Welches Produkt?: ");
        try {
            int index = Integer.parseInt(input);
            Product product = productContext.get(index);
            listener.onRemoveProductEvent(product);
        } catch (NumberFormatException e) {
            inputError(input);
        }
    }

    @Override
    public void listProductsEvent(List<Product> products)
    {
        productContext = products;
        for (int i = 0; i < products.size(); i++) {
            String header = "(" + i + "):";
            header = trim(header, 6);

            String name = products.get(i).getName();
            name = trim(name, 25);

            String platform = products.get(i).getPlatform();
            platform = trim(platform, 20);

            Console.print(MessageType.REQUEST, header);
            Console.println(MessageType.INFO, name + " " + platform);
        }
    }

    @Override
    public void listPlatformsEvent(List<Platform> platforms)
    {
        platformContext = platforms;
        for (int i = 0; i < platforms.size(); i++) {
            String header = "(" + i + "):";
            header = trim(header, 6);

            String name = platforms.get(i).getName();
            name = trim(name, 25);

            Console.print(MessageType.REQUEST, header);
            Console.println(MessageType.INFO, name);
        }
    }

    @Override
    public void listPricesEvent(List<Price> prices)
    {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (Price price : prices) {
            String header = dateFormat.format(price.timestamp());

            String priceString = String.format("%10s", price);

            Console.print(MessageType.REQUEST, header);
            Console.println(MessageType.INFO, priceString);
        }
    }

    @Override
    public void listCurrenciesEvent(List<Currency> currencies)
    {
        currencyContext = currencies;
        for (int i = 0; i < currencies.size(); i++) {
            String header = "(" + i + "):";
            header = trim(header, 6);

            String name = currencies.get(i).name();
            name = trim(name, 25);

            Console.print(MessageType.REQUEST, header);
            Console.println(MessageType.INFO, name);
        }
    }

    @Override
    public void onUpdateStartedEvent()
    {
        Console.println(MessageType.INFO, "\nUpdate begonnen.");
    }

    @Override
    public void onUpdateStartedEvent(Product product)
    {
        String name = product.getName();
        Console.print(MessageType.INFO, "Preis von \"" + name + "\" holen ... ");
    }

    @Override
    public void onPriceIncreased(Price newPrice, Product product)
    {
        Price oldPrice = product.getPrice();
        double priceIncrease = Math.abs(newPrice.value() - oldPrice.value());
        Console.print(MessageType.INFO, "-> Preissteigerung um ");
        Console.print(MessageType.ERROR, String.format(Locale.US, "%.2f %s", priceIncrease, product.getCurrency().getSymbol()));
        Console.println(MessageType.INFO, ". neuer Preis: " + newPrice);
    }

    @Override
    public void onPriceDecreased(Price newPrice, Product product)
    {
        Price oldPrice = product.getPrice();
        double priceDecrease = Math.abs(newPrice.value() - oldPrice.value());
        Console.print(MessageType.INFO, "-> Preissenkung um ");
        Console.print(MessageType.SUCCESS, String.format("%f.2", priceDecrease));
        Console.println(MessageType.INFO, ". neuer Preis: " + newPrice);
    }

    @Override
    public void onNoPriceChange(Product product)
    {
        Console.println(MessageType.INFO, "-> Keine Veränderung");
    }

    @Override
    public void onSuccess()
    {
        Console.println(MessageType.SUCCESS, "Operation erfolgreich ausgeführt");
    }

    @Override
    public void onError(Exception e)
    {
        Console.println(MessageType.ERROR, e.getMessage());
    }

    @Override
    public Product onRequestProduct(List<Product> allProducts)
    {
        listProductsEvent(allProducts);
        Product product = null;
        while (product == null) {
            String input = Console.read("Welches Produkt?: ");
            try {
                int index = Integer.parseInt(input);
                product = productContext.get(index);
            } catch (NumberFormatException e) {
                inputError(input);
            }
        }
        return product;
    }

    private String trim(String string, int length)
    {
        if (string.length() > length) {
            string = string.substring(0, length - 3) + "...";
        }
        return String.format("%-" + length + "s", string);
    }

    private void inputError(String input)
    {
        Console.println(MessageType.ERROR, "Unzulässige Eingabe \"" + input + "\"");
    }
}
