package com.iso53;

import org.apache.commons.io.FilenameUtils;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.Arrays;

public class Decrypter implements Runnable {

    private final Thread thread;
    private final Path path;

    public Decrypter(Path path) {
        this.path = path;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        decryptFile();
    }

    public void startDecryption() {
        this.thread.start();
    }

    private void decryptFile() {
        // Read file content
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Decrypt the file content
        byte[] decryptedContent = EncryptionManager.run(fileContent, EncryptionManager.KEY, Cipher.DECRYPT_MODE);

        // Get the original file name
        byte[] fileNameAsBytes = null;
        for (int i = fileContent.length - 1; i >= 0; i--) {
            if (String.valueOf(fileContent[i]).equals(System.lineSeparator())) {
                fileNameAsBytes = new byte[fileContent.length - i + 1];
                System.arraycopy(fileContent, i + 1, fileNameAsBytes, 0, fileNameAsBytes.length);
                break;
            }
        }

        // Save the decrypted content back to file
        try {
            if (decryptedContent != null) {
                Files.write(path, decryptedContent);

                // Rename the file with decrypted file name
                Path newPath = path.resolveSibling(new String(fileNameAsBytes, StandardCharsets.UTF_8));
                Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
