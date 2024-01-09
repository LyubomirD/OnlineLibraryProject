package com.example.online_library.login.encryptUserSession;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtils {

    private static SecretKey secretKey;

    private EncryptionUtils() {
    }

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        if (secretKey == null) {
            synchronized (EncryptionUtils.class) {
                // Double-check to ensure another thread hasn't created the key in the meantime
                if (secretKey == null) {
                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                    keyGenerator.init(128);
                    secretKey = keyGenerator.generateKey();
                }
            }
        }
        return secretKey;
    }

    public static String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
}
