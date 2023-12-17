package com.iso53;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public abstract class CipherManager {

    public static final SecretKey KEY = generateSecretKey();

    public static byte[] run(byte[] content, SecretKey key, int flag) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(flag, key);
            return cipher.doFinal(content);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException
                 | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static SecretKey generateSecretKey() {
        KeyGenerator keygen;
        try {
            keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom());
            SecretKey sc = keygen.generateKey();

            // get base64 encoded version of the key
            System.out.printf("\n\nKEY: %s", Base64.getEncoder().encodeToString(sc.getEncoded()));

            return sc;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
