package org.practica.utils;

import javax.crypto.SecretKey;
import java.io.File;

public class GeneratePassword {
    public static void main(String[] args) {
        String password = "javaApp";
        String routeKey = "src" + File.separator + "main"  + File.separator + "resources" + File.separator + "key";
        SecretKey decryption_key;
        try {
            decryption_key = PasswordEncryption.generateKey();
            PasswordEncryption.saveKeyToFile(decryption_key,routeKey);
            System.out.println(PasswordEncryption.encrypt(password,decryption_key));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
