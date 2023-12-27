package com.iso53;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.Arrays;

public class Encrypter implements Runnable {

    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Thread thread;
    private final Path path;

    public Encrypter(Path path) {
        this.path = path;
        this.thread = new Thread(this);
    }

    @Override
    public void run() {
        encryptFile();
    }

    public void startEncryption() {
        this.thread.start();
    }

    private void encryptFile() {
        // Read file content
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Append the filename as a new line to EOF, so we can find the file name and
        // extension after decryption
        String fileNameLine = System.lineSeparator() + path.getFileName().toString();
        byte[] fileNameLineBytes = fileNameLine.getBytes();
        byte[] contentWithFileName = concatArray(fileContent, fileNameLineBytes);

        // Encrypt the file content
        byte[] encryptedContent = CipherManager.run(contentWithFileName, CipherManager.KEY, Cipher.ENCRYPT_MODE);

        // Create random file name with extension
        byte[] encryptedFileNameWithExtension = concatArray(generateRandomFileName(), ".encrypted".getBytes(StandardCharsets.UTF_8));

        // Save the encrypted content back to file
        try {
            if (encryptedContent != null) {
                Files.write(path, encryptedContent);

                // Rename the file with encrypted file name
                Path newPath = path.resolveSibling(new String(encryptedFileNameWithExtension, StandardCharsets.UTF_8));
                Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while encrypting the file.");
        }
    }

    private byte[] generateRandomFileName() {
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder(128);

        for (int i = 0; i < 128; i++) {
            int randInt = rand.nextInt(ALLOWED_CHARACTERS.length());
            char randChar = ALLOWED_CHARACTERS.charAt(randInt);
            sb.append(randChar);
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private byte[] concatArray(byte[] array1, byte[] array2) {
        byte[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}
