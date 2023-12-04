import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Encrypter implements Runnable {

    Path path;
    Thread thread;

    public Encrypter(Path path) {
        this.path = path;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        // Read file content
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Append the filename as a new line to EOF so we can find the file name and
        // extension after decryption
        String fileNameLine = path.getFileName().toString() + System.lineSeparator();
        byte[] fileNameLineBytes = fileNameLine.getBytes();
        byte[] contentWithFileName = Arrays.copyOf(fileContent, fileContent.length + fileNameLineBytes.length);
        System.arraycopy(fileNameLineBytes, 0, contentWithFileName, fileContent.length, fileNameLineBytes.length);

        // Encrypt the file content
        byte[] encryptedContent = EncryptionManager.encrypt(contentWithFileName, EncryptionManager.KEY);

        // Save the encrypted content back to file
        try {
            Files.write(path, encryptedContent);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
