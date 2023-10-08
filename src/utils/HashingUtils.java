package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingUtils {


    public static String get_SHA_1_SecurePassword(String passwordToHash) {
        String generatedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
//            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
