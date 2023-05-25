package de.dhbw.pricetracker.plugins.ui;

import de.dhbw.pricetracker.adapters.Console;
import de.dhbw.pricetracker.application.MessageType;
import de.dhbw.pricetracker.application.ui.UIEventListener;
import de.dhbw.pricetracker.application.ui.UserInterface;
import de.dhbw.pricetracker.domain.CommonPlatform;
import de.dhbw.pricetracker.domain.CommonProduct;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

public class CommandLineInterface implements UserInterface {

    UIEventListener listener;

    @Override
    public void start() {
        while(true){
            String input = Console.read().toLowerCase();

            if (input.equals("add platform")){
                addPlatformEvent();
            } else if (input.equals("add platform")){
                addProductEvent();
            } else if (input.equals("add platform --help")){
                helpForAddPlatformEvent();
            } else if (input.equals("add product --help")){
                helpForAddProductEvent();
            } else if(input.equals("exit")){
                break;
            } else {
                Console.println(MessageType.ERROR, "unbekannter Befehl \"" + input + "\"");
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
}
