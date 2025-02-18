package lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import lab1.DataProcessor;

import java.math.BigDecimal;

class DataProcessorStatisticsTest {

    @Test
    void testShortStatistics() {
        CommandLineArgs commandLineArgs = new CommandLineArgs(new String[]{"-s"});
        DataProcessor dataProcessor = new DataProcessor(commandLineArgs);

        // Создаем тестовые данные
        dataProcessor.setIntCount(2);
        dataProcessor.setDoubleCount(1);
        dataProcessor.setStringCount(3);
        dataProcessor.setLongCount(4);

        // Проверяем краткую статистику
        dataProcessor.printStatistics(commandLineArgs);
    }

    @Test
    void testFullStatistics() {
        CommandLineArgs commandLineArgs = new CommandLineArgs(new String[]{"-#", "-s"});
        DataProcessor dataProcessor = new DataProcessor(commandLineArgs);

        // Создаем тестовые данные
        dataProcessor.setIntCount(2);
        dataProcessor.setDoubleCount(1);
        dataProcessor.setStringCount(3);
        dataProcessor.setLongCount(4);
        dataProcessor.setSum(BigDecimal.valueOf(100));
        dataProcessor.setMin(BigDecimal.valueOf(1));
        dataProcessor.setMax(BigDecimal.valueOf(1000));
        dataProcessor.setStringMinLength(5);
        dataProcessor.setStringMaxLength(20);

        // Проверяем полную статистику
        dataProcessor.printStatistics(commandLineArgs);
    }
}

