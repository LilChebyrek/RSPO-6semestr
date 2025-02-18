package lab1;

import java.util.List;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting file generation with random objects...");

        int THREAD_COUNT = 2;
        int TOTAL_OBJECTS = 1000;
        String FILENAME = "test.txt";

        // Создаем и запускаем потоки
        for (int i = 0; i < THREAD_COUNT; i++) {
            new MultiThreadProcessor.RandomObjectWriter(TOTAL_OBJECTS / THREAD_COUNT, FILENAME, i).start();
        }

        try {
            System.out.println("startRun");

            CommandLineArgs commandLineArgs = new CommandLineArgs(args);
            DataProcessor dataProcessor = new DataProcessor(commandLineArgs);
            List<String> filenames = commandLineArgs.getInputFiles();

            for (String filename : filenames) {
                try {
                    System.out.println("Setting filename: " + filename);
                    dataProcessor.setFilename(filename);
                    System.out.println("Run processFile");
                    dataProcessor.processFile();
                } catch (Exception e) {
                    System.out.println("Error uploading file " + filename + ": " + e.getMessage());
                }
            }

            dataProcessor.printStatistics(commandLineArgs);
            dataProcessor.writeResults();

        } catch (Exception e) {
            System.out.println("Error during file write: " + e.getMessage());
        }
    }
}
