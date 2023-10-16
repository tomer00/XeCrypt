package cipher;

import repo.Repo;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CipherUtils {

    public static String encrypt(String msg) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(Repo.getHash().substring(0, 32).getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            byte[] encrypted = cipher.doFinal(msg.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public static String decrypt(String msg) {
        try {

            byte[] values = Base64.getDecoder().decode(msg);
            SecretKeySpec skeySpec = new SecretKeySpec(Repo.getHash().substring(0, 32).getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);


            return new String(cipher.doFinal(values));

        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public static void encFile(File inpFile, File outFile) {
        try (var in = new FileInputStream(inpFile)) {
            SecretKeySpec skeySpec = new SecretKeySpec(Repo.getHash().substring(0, 32).getBytes(StandardCharsets.UTF_8), "AES");


            byte[] ivBytes = Repo.getRandom().substring(0, 16).getBytes(StandardCharsets.UTF_8);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            var out = (OutputStream) new FileOutputStream(outFile);

            out.write(ivBytes);
            out.flush();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            out = new CipherOutputStream(out, cipher);
            int count;
            byte[] buffer = new byte[1024];
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }

            in.close();
            out.flush();
            out.close();

        } catch (Exception ignored) {
        }
    }

    public static void decFile(File inpFile, File outFile) {
        try (var in = new FileInputStream(inpFile)) {
            SecretKeySpec skeySpec = new SecretKeySpec(Repo.getHash().substring(0, 32).getBytes(StandardCharsets.UTF_8), "AES");

            byte[] ivBytes = new byte[16];
            in.read(ivBytes);

            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            var out = (OutputStream) new FileOutputStream(outFile);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            out = new CipherOutputStream(out, cipher);
            int count;
            byte[] buffer = new byte[1024];
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            out.flush();
            out.close();
        } catch (Exception ignored) {
        }
    }

}
