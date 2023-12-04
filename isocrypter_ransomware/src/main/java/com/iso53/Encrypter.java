package com.iso53;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class Encrypter implements Runnable {

    public static final int FILE = 0;
    public static final int FOLDER = 1;
    private final Thread thread;
    private final Path path;
    private final int flag;

    public Encrypter(Path path, int flag) {
        this.path = path;
        this.thread = new Thread(this);
        this.flag = flag;
    }

    @Override
    public void run() {
        switch (flag) {
            case FILE: {
                encryptFile(this.path);
                break;
            }
            case FOLDER: {
                encryptFolder(this.path);
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + flag);
            }
        }
    }

    private void encryptFile(Path path) {
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
        String fileNameLine = path.getFileName().toString() + System.lineSeparator();
        byte[] fileNameLineBytes = fileNameLine.getBytes();
        byte[] contentWithFileName = concatArray(fileContent, fileNameLineBytes);

        // Encrypt the file content
        byte[] encryptedContent = EncryptionManager.encrypt(contentWithFileName, EncryptionManager.KEY);

        // Create encrypted file name with extension
        byte[] fileName = FilenameUtils.removeExtension(path.getFileName().toString()).getBytes(StandardCharsets.UTF_8);
        byte[] encryptedFileName = EncryptionManager.encrypt(fileName, EncryptionManager.KEY);
        byte[] encryptedFileNameWithExtension = concatArray(encryptedFileName, ".encrypted".getBytes(StandardCharsets.UTF_8));

        // Save the encrypted content back to file
        try {
            if (encryptedContent != null) {
                Files.write(path, encryptedContent);

                // Rename the file with encrypted file name
                Path newPath = path.resolveSibling(new String(encryptedFileNameWithExtension, StandardCharsets.UTF_8));
                Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void encryptFolder(Path path) {

    }

    private byte[] concatArray(byte[] array1, byte[] array2) {
        byte[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }
}
