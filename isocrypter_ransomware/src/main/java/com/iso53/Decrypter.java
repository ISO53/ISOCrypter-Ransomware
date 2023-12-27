package com.iso53;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Decrypter implements Runnable {

    private final Thread thread;
    private final Path path;
    private final SecretKey key;

    public Decrypter(Path path, SecretKey key) {
        this.path = path;
        this.thread = new Thread(this);
        this.key = key;
    }

    @Override
    public void run() {
        decryptFile();
    }

    public void startDecryption() {
        this.thread.start();
    }

    private void decryptFile() {
        // If the file is not an encrypted file, ignore it
        if (!path.getFileName().toString().endsWith(".encrypted")) {
            return;
        }

        // Read file content
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Decrypt the file content
        byte[] decryptedContent = CipherManager.run(fileContent, key, Cipher.DECRYPT_MODE);

        if (decryptedContent == null) {
            System.out.println("This file couldn't be decrypted.");
            return;
        }

        // Get the original file name
        int lastLineSeparatorIndex = -1;
        byte[] lineSeparatorBytes = System.lineSeparator().getBytes(StandardCharsets.UTF_8);

        for (int i = decryptedContent.length - lineSeparatorBytes.length; i >= 0; i--) {
            boolean isLineSeparator = true;
            for (int j = 0; j < lineSeparatorBytes.length; j++) {
                if (decryptedContent[i + j] != lineSeparatorBytes[j]) {
                    isLineSeparator = false;
                    break;
                }
            }
            if (isLineSeparator) {
                lastLineSeparatorIndex = i;
                break;
            }
        }

        // Extract the last line
        String fileNameAsStr = "";
        if (lastLineSeparatorIndex != -1) {
            int lineLength = decryptedContent.length - lastLineSeparatorIndex - lineSeparatorBytes.length;
            byte[] lastLineBytes = new byte[lineLength];
            System.arraycopy(decryptedContent, lastLineSeparatorIndex + lineSeparatorBytes.length, lastLineBytes, 0, lineLength);
            fileNameAsStr = new String(lastLineBytes, StandardCharsets.UTF_8);

            // Remove the last line from decryptedContent
            byte[] newDecryptedContent = new byte[lastLineSeparatorIndex + lineSeparatorBytes.length];
            System.arraycopy(decryptedContent, 0, newDecryptedContent, 0, lastLineSeparatorIndex + lineSeparatorBytes.length);
            decryptedContent = newDecryptedContent;
        }

        // Save the decrypted content back to file
        try {
            Files.write(path, decryptedContent);

            // Rename the file with decrypted file name
            Path newPath = path.resolveSibling(fileNameAsStr);
            Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("An error occurred while decrypting the file.");
        }
    }
}
