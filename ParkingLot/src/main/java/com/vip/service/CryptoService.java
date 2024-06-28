package com.vip.service;

import com.vip.common.utility.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class CryptoService {

    @Value("${otp.encryption.secretKey}")
    private String secretKey;

    public String encryptData(String rawPassword) throws Exception {

        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 65536, 256);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encryptedBytes = cipher.doFinal(rawPassword.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptData(String encryptedPassword) throws Exception {

        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 65536, 256); // Adjust parameters as needed
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, "UTF-8");
    }


}
