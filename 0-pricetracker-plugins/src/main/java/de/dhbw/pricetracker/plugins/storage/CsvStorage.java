package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.adapters.storage.Storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CsvStorage<T> implements Storage<T>
{
    final String csvDelimiter;
    private final File csvFile;
    private final List<T> entities;

    public CsvStorage()
    {
        String directory = System.getProperty("user.home");
        String filename = "pricetracker_entity.csv";

        this.entities = new ArrayList<>();
        this.csvFile = new File(directory, filename);
        this.csvDelimiter = ";";
        createFileIfMissing(csvFile);
        readEntitiesFromCsv();
    }

    public CsvStorage(File csvFile, String csvDelimiter)
    {
        this.entities = new ArrayList<>();
        this.csvFile = csvFile;
        this.csvDelimiter = csvDelimiter;
        createFileIfMissing(this.csvFile);
        readEntitiesFromCsv();
    }

    private void readEntitiesFromCsv()
    {
        try (BufferedReader entityReader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = entityReader.readLine()) != null) {
                T newEntity = csvStringToEntity(line);

                entities.add(newEntity);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFileIfMissing(File file)
    {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void appendToFile(File file, String string)
    {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file, true))) {
            pw.println(string);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    abstract String entityToCsvString(T entity);

    abstract T csvStringToEntity(String csvLine);

    @Override
    public List<T> getAll()
    {
        return entities;
    }

    @Override
    public void add(T entity)
    {
        entities.add(entity);
        String csvLine = entityToCsvString(entity);
        appendToFile(csvFile, csvLine);
    }

    @Override
    public void remove(T entity)
    {
        csvFile.delete();
        createFileIfMissing(csvFile);
        entities.remove(entity);
        for (T currentEntity : entities) {
            String csvLine = entityToCsvString(currentEntity);
            appendToFile(csvFile, csvLine);
        }
    }
}
