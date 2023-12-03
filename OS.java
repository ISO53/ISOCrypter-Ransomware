import java.util.HashSet;

public abstract class OS {

    public static final int WINDOWS = 0;
    public static final int LINUX = 1;

    public abstract static class Windows{

        private static final HashSet<String> EXCLUDED_FOLDERS = new HashSet<>();

        static {
            EXCLUDED_FOLDERS.add("windows");
            EXCLUDED_FOLDERS.add("boot");
            EXCLUDED_FOLDERS.add("documents and settings");
            EXCLUDED_FOLDERS.add("program files");
            EXCLUDED_FOLDERS.add("program files (x86)");
            EXCLUDED_FOLDERS.add("recovery");
            EXCLUDED_FOLDERS.add("program data");
            EXCLUDED_FOLDERS.add("system volume information");
            EXCLUDED_FOLDERS.add("$recycle.bin");
            EXCLUDED_FOLDERS.add("temp");
            EXCLUDED_FOLDERS.add("pagefile.sys");
            EXCLUDED_FOLDERS.add("swapfile.sys");
        }

        public static HashSet<String> getExcludedFolders() {
            return EXCLUDED_FOLDERS;
        }
    }

    public abstract static class Linux {
        private static final HashSet<String> EXCLUDED_FOLDERS = new HashSet<>();

        static {
            EXCLUDED_FOLDERS.add("proc");
            EXCLUDED_FOLDERS.add("sys");
            EXCLUDED_FOLDERS.add("dev");
            EXCLUDED_FOLDERS.add("run");
            EXCLUDED_FOLDERS.add("tmp");
            EXCLUDED_FOLDERS.add(".snapshots");
            EXCLUDED_FOLDERS.add("mnt");
            EXCLUDED_FOLDERS.add("lost+found");
            EXCLUDED_FOLDERS.add("boot");
            EXCLUDED_FOLDERS.add("var");
        }

        public static HashSet<String> getExcludedFolders() {
            return EXCLUDED_FOLDERS;
        }
    }
}
