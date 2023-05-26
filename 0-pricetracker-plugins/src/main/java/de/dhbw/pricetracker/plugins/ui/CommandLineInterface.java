package de.dhbw.pricetracker.plugins.ui;

import de.dhbw.pricetracker.adapters.Console;
import de.dhbw.pricetracker.application.MessageType;
import de.dhbw.pricetracker.application.ui.UIEventListener;
import de.dhbw.pricetracker.application.ui.UserInterface;
import de.dhbw.pricetracker.domain.CommonPlatform;
import de.dhbw.pricetracker.domain.CommonProduct;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

import java.util.Locale;
import java.util.Map;

public class CommandLineInterface implements UserInterface {

    UIEventListener listener;

    Map<String, Platform> platformContext;
    Map<String, Product> productContext;

    @Override
    public void start() {
        loop:
        while(true){
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
    public void setUIEventListener(UIEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void helpForAddPlatformEvent() {
        Console.println("Um dem PriceTracker eine Platform hinzuzufügen, benötigst du folgenden Daten:");
        Console.print(MessageType.REQUEST, "Name: ");
        Console.println("Ein selbst gewählter Name.");
        Console.print(MessageType.REQUEST, "Preisselektor: ");
        Console.println("Ein CSS-Selektor, welcher den Preis der Produkte der Platform eindeutig bestimmt. Das System unterstützt Element- und Klassenselektoren. Beispiel: \"span.priceToPay\"");
    }

    @Override
    public void addPlatformEvent() {
        String name = Console.read("Name: ");
        String priceIdentifier = Console.read("Preisselektor: ");

        listener.onAddPlatformEvent(new CommonPlatform(name, priceIdentifier));
    }

    @Override
    public void removePlatformEvent() {
        Console.println(MessageType.WARN, "Achtung: Alle dazugehörigen Produkte werden ebenfalls gelöscht!");
        listener.onListPlatformsEvent();
        String input = Console.read("Welche Platform?: ");
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e){
            inputError(input);
        }
    }

    @Override
    public void helpForAddProductEvent() {
    }

    @Override
    public void addProductEvent() {
        String name = Console.read("Name: ");
        String platform = Console.read("Platform: ");
        String url = Console.read("URL: ");

        Product product = new CommonProduct(name, platform, url);
        listener.onAddProductEvent(product);
    }

    @Override
    public void removeProductEvent() {
        listener.onListProductsEvent();
        String input = Console.read("Welches Produkt?: ");
        try {
            Integer.parseInt(input);
            Product product = pr
        } catch (NumberFormatException e){
            inputError(input);
        }
    }

    @Override
    public void listProductsEvent(Map<String, Product> products) {
        productContext = products;
        int i = 0;
        for (Map.Entry<String, Product> productEntry: products.entrySet()) {
            String header = "(" + i + "):";
            header = trim(header, 6);

            String name = productEntry.getKey();
            name = trim(name, 25);

            String platform = productEntry.getValue().getPlatform();
            platform = trim(platform, 20);

            Console.print(MessageType.REQUEST, header);
            Console.println(MessageType.INFO, name + " " + platform);

            i++;
        }
    }

    @Override
    public void listPlatformsEvent(Map<String, Platform> platforms) {
        platformContext = platforms;
        int i = 0;
        for (Map.Entry<String, Platform> platformEntry: platforms.entrySet()) {
            String header = "(" + i + "):";
            header = trim(header, 6);

            String name = platformEntry.getKey();
            name = trim(name, 25);

            Console.print(MessageType.REQUEST, header);
            Console.println(MessageType.INFO, name);

            i++;
        }
    }

    @Override
    public void onUpdateStartedEvent() {
        Console.println(MessageType.INFO, "\nUpdate begonnen.");
    }

    @Override
    public void onUpdateStartedEvent(Product product) {
        String name = product.getName();
        name = trim(name, 20);
        Console.print(MessageType.INFO, "Preis von \"" + name + "\" holen ... ");
    }

    @Override
    public void onPriceIncreased(double newPrice, Product product) {
        double oldPrice = product.getPrice();
        double priceIncrease = Math.abs(newPrice - oldPrice);
        Console.print(MessageType.INFO, "-> Preissteigerung um ");
        Console.print(MessageType.ERROR, String.format(Locale.US, "%.2f", priceIncrease));
        Console.println(MessageType.INFO, ". neuer Preis: " + newPrice);
    }

    @Override
    public void onPriceDecreased(double newPrice, Product product) {
        double oldPrice = product.getPrice();
        double priceDecrease = Math.abs(newPrice - oldPrice);
        Console.print(MessageType.INFO, "-> Preissenkung um ");
        Console.print(MessageType.SUCCESS, String.format("%f.2", priceDecrease));
        Console.println(MessageType.INFO, ". neuer Preis: " + newPrice);
    }

    @Override
    public void onNoPriceChange(Product product) {
        Console.println(MessageType.INFO, "-> Keine Veränderung");
    }

    @Override
    public void info(String message) {
        Console.println(MessageType.INFO, message);
    }

    @Override
    public void success(String message) {
        Console.println(MessageType.SUCCESS, message);
    }

    @Override
    public void error(String message) {
        Console.println(MessageType.ERROR, message);
    }

    @Override
    public void warning(String message) {
        Console.println(MessageType.WARN, message);
    }

    private String trim(String string, int length){
        if(string.length() > length){
            string = string.substring(0, length-3) + "...";
        }
        return String.format("%-" + length + "s", string);
    }

    private void inputError(String input) {
        Console.println(MessageType.ERROR, "Unzulässige Eingabe \"" + input + "\"");
    }
}
