package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.domain.CommonPlatform;
import de.dhbw.pricetracker.domain.Platform;
import de.dhbw.pricetracker.domain.Product;

import java.io.*;
import java.util.*;

public class CsvPlatformStorage implements Storage<Platform> {
    private static final String csvDelimiter = ";";
    private static final String directory = System.getProperty("user.home");
    private static final String filename = "pricetracker_platform.csv";

    private File platformCsv;
    private List<Platform> platforms;

    public CsvPlatformStorage(){
        this.platforms = new ArrayList();
        this.platformCsv = new File(directory, filename);
        createFileIfMissing(platformCsv);
        readPlatformsFromCsv();
    }

    private void readPlatformsFromCsv() {
        try (BufferedReader platformReader = new BufferedReader(new FileReader(platformCsv))){
            String line;
            while ((line = platformReader.readLine()) != null) {
                String[] values = line.split(csvDelimiter);

                String name = values[0];
                String priceIdentifier = values[1];

                Platform newPlatform = new CommonPlatform(name, priceIdentifier);

                platforms.add(newPlatform);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFileIfMissing(File file) {
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void appendToFile(File file, String string) {
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(file, true))) {
            pw.println(string);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String platformToCsvString(Platform platform){
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(platform.getName());
        joiner.add(platform.getPriceSelector());
        return joiner.toString();
    }

    @Override
    public List<Platform> getAll() {
        return platforms;
    }

    @Override
    public void add(Platform entity){
        String csvLine = platformToCsvString(entity);
        appendToFile(platformCsv, csvLine);
    }

    @Override
    public void remove(Platform entity) {
        platformCsv.delete();
        createFileIfMissing(platformCsv);
        for (Platform platform: platforms) {
            if(platform.equals(entity)){
                platforms.remove(platform);
            } else {
                String csvLine = platformToCsvString(entity);
                appendToFile(platformCsv, csvLine);
            }
        }
    }
}
