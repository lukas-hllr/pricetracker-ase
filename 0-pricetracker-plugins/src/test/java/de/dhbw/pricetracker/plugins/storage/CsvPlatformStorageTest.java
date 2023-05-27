package de.dhbw.pricetracker.plugins.storage;

import de.dhbw.pricetracker.adapters.storage.Storage;
import de.dhbw.pricetracker.domain.CommonPlatform;
import de.dhbw.pricetracker.domain.Platform;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CsvPlatformStorageTest {

    File testCsv;
    String csvDelimiter = ";";
    Storage<Platform> platformStorage;

    static Platform[] testPlatforms = new Platform[]{
            new CommonPlatform("TestPlatform0", "TestSelector0"),
            new CommonPlatform("TestPlatform1", "TestSelector1"),
            new CommonPlatform("TestPlatform2", "TestSelector2"),
            new CommonPlatform("TestPlatform3", "TestSelector3"),
            new CommonPlatform("TestPlatform4", "TestSelector4"),
    };

    static Platform[] initialPlatforms = new Platform[]{
            new CommonPlatform("TestPlatform0", "TestSelector0"),
            new CommonPlatform("TestPlatform1", "TestSelector1"),
            new CommonPlatform("TestPlatform2", "TestSelector2"),
    };

    @BeforeEach
    void beforeEach(){
        testCsv = new File("src/test/resources", "platform-storage-test.csv");
        deleteAndCreateNewFile(testCsv, initialPlatforms);
        platformStorage = new CsvPlatformStorage(testCsv, csvDelimiter);
    }

    @Test
    void testInitialContents() {
        assertEquals(3, countLines(testCsv));
        assertEquals(List.of(initialPlatforms), platformStorage.getAll());
    }
    @Test
    void testAdd() {
        platformStorage.add(testPlatforms[3]);
        platformStorage.add(testPlatforms[4]);

        List<Platform> expected = new ArrayList<>(){{
            add(testPlatforms[0]);
            add(testPlatforms[1]);
            add(testPlatforms[2]);
            add(testPlatforms[3]);
            add(testPlatforms[4]);
        }};

        assertEquals(expected, platformStorage.getAll());
        assertEquals(5, countLines(testCsv));
    }

    @Test
    void testRemove() {
        platformStorage.remove(testPlatforms[1]);

        assertEquals(2, platformStorage.getAll().size());
        assertEquals(2, countLines(testCsv));
        assertFalse(platformStorage.getAll().contains(testPlatforms[1]));
        String csvString = platformToCsvString(testPlatforms[1]);
        assertFalse(fileContainsLine(testCsv, csvString));
    }

    private void deleteAndCreateNewFile(File file, Platform[] platforms) {
        file.delete();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        initCsvContent(file, platforms);
    }

    private boolean fileContainsLine(File file, String line){
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if(currentLine.equals(line)) {
                    return true;
                }
            }
        } catch(FileNotFoundException ignore) {}
        return false;
    }

    private int countLines(File file){
        int lines = 0;
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                lines++;
                scanner.nextLine();
            }
        } catch(FileNotFoundException ignore) {}
        return lines;
    }

    private String platformToCsvString(Platform platform){
        StringJoiner joiner = new StringJoiner(csvDelimiter);
        joiner.add(platform.getName());
        joiner.add(platform.getPriceSelector());
        return joiner.toString();
    }

    private void initCsvContent(File file, Platform[] platforms) {
        try(PrintWriter pw = new PrintWriter(new FileOutputStream(file, true))) {
            for (Platform platform: platforms) {
                String csvLine = platformToCsvString(platform);
                pw.println(csvLine);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}