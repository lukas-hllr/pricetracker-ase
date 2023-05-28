package de.dhbw.pricetracker.plugins.ui.console;

import java.util.Scanner;

public class Console
{

    private static String getTypeColorIdentifier(MessageType type)
    {
        return (char) 27 + "[" + type.value + "m";
    }

    public static void print(String message)
    {
        print(MessageType.INFO, message);
    }

    public static void print(MessageType type, String message)
    {
        String typeColorIdentifier = getTypeColorIdentifier(type);
        String defaultTypeColorIdentifier = getTypeColorIdentifier(MessageType.INFO);

        System.out.print(typeColorIdentifier + message + defaultTypeColorIdentifier);
    }

    public static void println(String message)
    {
        print(message + "\n");
    }

    public static void println(MessageType type, String message)
    {
        print(type, message + "\n");
    }

    public static String read(String message)
    {
        print(MessageType.REQUEST, message);
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public static String read()
    {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
