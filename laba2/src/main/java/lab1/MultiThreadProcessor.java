package lab1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MultiThreadProcessor {
    private static final int THREAD_COUNT = 4;
    private static final int TOTAL_OBJECTS = 100;
    private static final String FILENAME = "output.txt";
    private static final Random RANDOM = new Random();

    private static String getRandomString() {
        String symbols = "abcdefghijklmnopqrstuvwxyz";
        int size = RANDOM.nextInt(symbols.length()) + 1;
        return RANDOM.ints(size, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private static Object getRandomObject() {
        switch (RANDOM.nextInt(4)) {
            case 0: return RANDOM.nextInt();
            case 1: return RANDOM.nextBoolean();
            case 2: return RANDOM.nextDouble();
            case 3: return getRandomString();
            default: return null;
        }
    }

    static class RandomObjectWriter extends Thread {
        private final int count;
        private final String filename;
        private final int threadId;

        public RandomObjectWriter(int count, String filename, int threadId) {
            this.count = count;
            this.filename = filename;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            try (FileWriter writer = new FileWriter(filename, true)) { // Append to file
                for (int i = 0; i < count; i++) {
                    writer.write(getRandomObject().toString() + "\n");
                    printProgress(threadId, (i + 1) * 100 / count); // Обновляем прогресс в процентах
                    Thread.sleep(10);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        private synchronized void printProgress(int threadId, int percent) {
            StringBuilder progressBar = new StringBuilder("Thread " + (threadId + 1) + ": [");
            int width = 50; // Ширина прогресс-бара
            int i = (percent * width) / 100;

            // Строим прогресс-бар
            for (int j = 0; j < width; j++) {
                if (j < i) {
                    progressBar.append("#");
                } else {
                    progressBar.append(" ");
                }
            }
            progressBar.append("] ").append(percent).append("%");

            // Перезаписываем только строку, соответствующую текущему потоку
            System.out.print(progressBar.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting file generation with random objects...");

        // Выводим заголовки для каждого потока
        for (int i = 0; i < THREAD_COUNT; i++) {
            System.out.print("Thread " + (i + 1) + ": [                                                  ] 0%\n");
        }

        // Создаем и запускаем потоки
        for (int i = 0; i < THREAD_COUNT; i++) {
            new RandomObjectWriter(TOTAL_OBJECTS / THREAD_COUNT, FILENAME, i).start();
        }
    }
}
