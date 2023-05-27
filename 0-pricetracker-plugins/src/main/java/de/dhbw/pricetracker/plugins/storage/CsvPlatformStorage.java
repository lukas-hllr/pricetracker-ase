package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.domain.CommonPlatform;
import de.dhbw.pricetracker.domain.Platform;

import java.io.File;
import java.util.StringJoiner;

public class CsvPlatformStorage extends CsvStorage<Platform>
{

    public CsvPlatformStorage()
    {
        super(new File(System.getProperty("user.home"),"pricetracker_platforms.csv"), ";");
    }

    public CsvPlatformStorage(File csvFile, String csvDelimiter)
    {
        super(csvFile, csvDelimiter);
    }

    @Override
    String entityToCsvString(Platform entity)
    {
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(entity.getName());
        joiner.add(entity.getPriceSelector());
        return joiner.toString();
    }

    @Override
    Platform csvStringToEntity(String csvLine)
    {
        String[] values = csvLine.split(csvDelimiter);

        String name = values[0];
        String priceIdentifier = values[1];

        return new CommonPlatform(name, priceIdentifier);
    }
}
