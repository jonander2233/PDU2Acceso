package org.practica.utils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class PasswordEncryption {

    // Genera una clave secreta AES
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // Tamaño de clave: 128 bits
        return keyGen.generateKey();
    }

    // Encripta un texto usando la clave secreta
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Desencripta un texto usando la clave secreta
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] originalData = cipher.doFinal(decodedData);
        return new String(originalData);
    }
    public static void saveKeyToFile(SecretKey secretKey, String filePath) throws IOException, IOException {
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        Files.write(new File(filePath).toPath(), encodedKey.getBytes());
        System.out.println("Key saved to file: " + filePath);
    }

    // Lee la clave desde un archivo y la reconstruye como SecretKey
    public static SecretKey loadKeyFromFile(String filePath, String algorithm) throws IOException {
        byte[] encodedKey = Files.readAllBytes(new File(filePath).toPath());
        byte[] decodedKey = Base64.getDecoder().decode(new String(encodedKey));
        return new SecretKeySpec(decodedKey, algorithm);
    }

}

