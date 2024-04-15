package com.vip.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class PasswordEncoderService {

    public static String encodePassword(String rawPassword, String secretKey) throws Exception {
        // Generate a secret key from the provided secretKey string
        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 65536, 256); // Adjust parameters as needed
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Initialize the cipher for encryption
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // Encrypt the raw password
        byte[] encryptedBytes = cipher.doFinal(rawPassword.getBytes("UTF-8"));

        // Encode the encrypted bytes as a Base64 string
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String verifyPassword(String encryptedPassword, String secretKey) throws Exception {
        // Generate a secret key from the provided secretKey string
        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 65536, 256); // Adjust parameters as needed
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Initialize the cipher for decryption
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        // Decode the Base64 string to get the encrypted bytes
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);

        // Decrypt the encrypted bytes
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // Convert the decrypted bytes back to a string
        return new String(decryptedBytes, "UTF-8");
    }



}
