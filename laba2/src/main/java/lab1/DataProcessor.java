package lab1;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

class DataProcessor {
    private final CommandLineArgs commandLineArgs;
    private String filename;
    private int intCount = 0;
    private int doubleCount = 0;
    private int stringCount = 0;
    private int longCount = 0;

    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.valueOf(Long.MAX_VALUE);
    private BigDecimal max = BigDecimal.valueOf(Long.MIN_VALUE);
    private double stringMinLength = Double.MAX_VALUE;
    private double stringMaxLength = 0;

    public List<Object> dataList = new ArrayList<Object>();

    private List<Integer> integers = Collections.synchronizedList(new ArrayList<>());
    private List<Double> doubles = Collections.synchronizedList(new ArrayList<>());
    private List<String> strings = Collections.synchronizedList(new ArrayList<>());
    private List<Long> longs = Collections.synchronizedList(new ArrayList<>());

    public DataProcessor(CommandLineArgs commandLineArgs) {
        this.commandLineArgs = commandLineArgs;
    }

    public void processFile() throws IOException {
        System.out.println("processFile started");
        List<String> lines = readFile(filename);

        // Обрабатываем данные последовательно
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!line.isEmpty()) {
                processLine(line);
            }
        }

        // Выводим статистику
//        printStatistics(commandLineArgs);
    }

    private List<String> readFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        }
        return lines;
    }

    private void processLine(String line) {
        List<Object> dataList = this.dataList;

        if (line.matches("^-?\\d+$")) {
            int num = Integer.parseInt(line);
            integers.add(num);
            intCount++;
            dataList.add(num);
            updateStats(num);
        } else if (line.matches("^-?\\d+[lL]?$")) {
            long num = Long.parseLong(line.replaceAll("[lL]$", ""));
            longs.add(num);
            longCount++;
            dataList.add(num);
            updateStats(num);
        } else if (line.matches("^-?\\d*\\.\\d+$")) {
            double num = Double.parseDouble(line);
            doubles.add(num);
            doubleCount++;
            dataList.add(num);
            updateStats(num);
        } else {
            strings.add(line);
            stringCount++;
            stringMinLength = Math.min(stringMinLength, line.length());
            stringMaxLength = Math.max(stringMaxLength, line.length());
            dataList.add(line);
        }
    }

    private void updateStats(Number num) {
        BigDecimal value = new BigDecimal(num.toString());
        sum = sum.add(value);
        min = min.min(value);
        max = max.max(value);
    }

    public void printStatistics(CommandLineArgs commandLineArgs) {
        if(commandLineArgs.isShortStats()) {
            System.out.println("short stat:");
            System.out.println("int: " + intCount);
            System.out.println("double: " + doubleCount);
            System.out.println("string: " + stringCount);
            System.out.println("long: " + longCount);
        }
        if (commandLineArgs.isFullStats()) {
            System.out.println("full stat:");
            System.out.println("int: " + intCount);
            System.out.println("double: " + doubleCount);
            System.out.println("string: " + stringCount);
            System.out.println("long: " + longCount);
            System.out.println("sum: " + sum);
            System.out.println("max: " + max);
            System.out.println("min: " + min);
            if (intCount + longCount + doubleCount > 0) {
                System.out.println("avg: " + sum.divide(BigDecimal.valueOf(intCount + longCount + doubleCount), 5, BigDecimal.ROUND_HALF_UP));
            }
            System.out.println("min string: " + stringMinLength);
            System.out.println("max string: " + stringMaxLength);
        }
    }

    public void writeResults() throws IOException {
        if (!integers.isEmpty()) writeFile("int.txt", integers);
        if (!doubles.isEmpty()) writeFile("double.txt", doubles);
        if (!strings.isEmpty()) writeFile("string.txt", strings);
        if (!longs.isEmpty()) writeFile("long.txt", longs);
    }

    private <T> void writeFile(String fileName, List<T> data) throws IOException {
        String filePath = commandLineArgs.getOutputPath() + commandLineArgs.getPrefix() + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, commandLineArgs.isAppend()))) {
            for (T item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        }
    }

    public void setFilename(String filename) { this.filename = filename; }
    public List<Object> getDataList() { return this.dataList; }
    public String getFilename() { return this.filename; }
    public int getIntCount() { return this.intCount; }
    public int getDoubleCount() { return this.doubleCount; }
    public int getStringCount() { return this.stringCount; }
    public void setIntCount(int intCount) { this.intCount = intCount; }
    public void setDoubleCount(int doubleCount) { this.doubleCount = doubleCount; }
    public void setStringCount(int stringCount) { this.stringCount = stringCount; }
    public void setLongCount(int longCount) { this.longCount = longCount; }
    public void setSum(BigDecimal sum) { this.sum = sum; }
    public void setMin(BigDecimal min) { this.min = min; }
    public void setMax(BigDecimal max) { this.max = max; }
    public void setStringMinLength(int stringMinLength) { this.stringMinLength = stringMinLength; }
    public void setStringMaxLength(int stringMaxLength) { this.stringMaxLength = stringMaxLength; }
    public void setIntegers(int integers) { this.integers.add(integers); }
    public void setDoubles(double doubles) { this.doubles.add(doubles); }
    public void setStrings(String strings) { this.strings.add(strings); }
}