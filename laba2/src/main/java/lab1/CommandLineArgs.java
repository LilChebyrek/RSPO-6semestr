package lab1;

import java.util.ArrayList;
import java.util.List;

class CommandLineArgs {
    private List<String> inputFiles = new ArrayList<>();
    private String outputPath = "./";
    private String prefix = "";
    private boolean append = false;
    private boolean shortStats = false;
    private boolean fullStats = false;

    public CommandLineArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    outputPath = args[++i];
                    break;
                case "-r":
                    prefix = args[++i];
                    break;
                case "-a":
                    append = true;
                    break;
                case "-s":
                    shortStats = true;
                    break;
                case "-#":
                    fullStats = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppend() {
        return append;
    }

    public boolean isShortStats() {
        return shortStats;
    }

    public boolean isFullStats() {
        return fullStats;
    }
}
