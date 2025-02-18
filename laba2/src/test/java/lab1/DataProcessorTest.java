package lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.List;

class DataProcessorTest {

    @Test
    void testProcessFile() throws IOException {
        CommandLineArgs commandLineArgs = new CommandLineArgs(new String[]{"file1.txt"});
        DataProcessor dataProcessor = new DataProcessor(commandLineArgs);

        // Создаем временный файл для тестирования
        File tempFile = File.createTempFile("testFile", ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("123\n");
        writer.write("456\n");
        writer.write("abc\n");
        writer.write("789.123\n");
        writer.close();

        dataProcessor.setFilename(tempFile.getAbsolutePath());
        dataProcessor.processFile();
        List<Object> data = dataProcessor.getDataList();

        // Проверяем, что данные были обработаны
        assertEquals(4, data.size());
        assertTrue(data.contains(123));
        assertTrue(data.contains(456));
        assertTrue(data.contains("abc"));
        assertTrue(data.contains(789.123));
    }

    @Test
    void testProcessData() throws IOException {
        CommandLineArgs commandLineArgs = new CommandLineArgs(new String[]{"file1.txt"});
        DataProcessor dataProcessor = new DataProcessor(commandLineArgs);

        // Создаем временный файл для тестирования
        File tempFile = File.createTempFile("testFile", ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("123\n");
        writer.write("456\n");
        writer.write("abc\n");
        writer.write("789.123\n");
        writer.close();

        dataProcessor.setFilename(tempFile.getAbsolutePath());
        dataProcessor.processFile();
        List<Object> data = dataProcessor.getDataList();

        // Проверяем статистику
        assertEquals(2, dataProcessor.getIntCount());
        assertEquals(1, dataProcessor.getDoubleCount());
        assertEquals(1, dataProcessor.getStringCount());
    }

    @Test
    void testWriteResults() throws IOException {
        CommandLineArgs commandLineArgs = new CommandLineArgs(new String[]{"-o", "src/", "-r", "prefix_"});
        DataProcessor dataProcessor = new DataProcessor(commandLineArgs);

        // Создаем тестовые данные
        dataProcessor.setIntegers(123);
        dataProcessor.setDoubles(456.78);
        dataProcessor.setStrings("test");

        // Пишем данные в файлы
        dataProcessor.writeResults();

        // Проверяем, что файлы созданы
        File intFile = new File("src/prefix_int.txt");
        File doubleFile = new File("src/prefix_double.txt");
        File stringFile = new File("src/prefix_string.txt");

        assertTrue(intFile.exists());
        assertTrue(doubleFile.exists());
        assertTrue(stringFile.exists());

        // Проверяем содержимое файлов
        BufferedReader reader = new BufferedReader(new FileReader(intFile));
        assertEquals("123", reader.readLine());
        reader.close();

        reader = new BufferedReader(new FileReader(doubleFile));
        assertEquals("456.78", reader.readLine());
        reader.close();

        reader = new BufferedReader(new FileReader(stringFile));
        assertEquals("test", reader.readLine());
        reader.close();
    }
}