package repo;

import cipher.Cipher;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Repo {
    public static final String PATH = "/home/tom/test/xcrypt/";
    public static final String ENC_NAME = "dHF0pywK8FvtBOQ27RoezfsXuKRhaybM";
    public static final String THUMB_NAME = "YrjmD2Dt97ambqy4GUeStRgy3AODGCPp";

    //region STATIC BLOCK


    public static void saveHash(String pass) {
        File fHash = new File(PATH + "hash");
        try (var ois = new FileOutputStream(fHash)) {
            ois.write(pass.getBytes(StandardCharsets.UTF_8));
            ois.flush();
        } catch (Exception ignored) {
        }
    }

    public static String getRandom() {
        var r = new Random();
        StringBuilder allowed = new StringBuilder();
        StringBuilder random = new StringBuilder();
        var al = "abcdefghijklmnopqrstuvwxyz";
        allowed.append(al);
        allowed.append(al.toUpperCase());
        allowed.append("0123456789");

        int i = 32;
        while (i-- != 0)
            random.append(allowed.charAt(r.nextInt(62)));

        return random.toString();
    }

    public static String getHash() {
        File fHash = new File(PATH + "hash");
        try (var ins = new BufferedReader(new InputStreamReader(new FileInputStream(fHash)))) {
            return ins.readLine();
        } catch (Exception ignored) {
        }
        return "";
    }


    //endregion STATIC BLOCK

    private final String path;

    private BufferedImage getSquareCropped(String file) throws Exception {
        File inputFile = new File(file);
        BufferedImage inputImage = ImageIO.read(inputFile);

        int w = inputImage.getWidth();
        int h = inputImage.getHeight();
        if (w == h) return inputImage;

        BufferedImage thumbnail;
        if (w > h) {
            thumbnail = new BufferedImage(h, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = thumbnail.createGraphics();
            g2d.drawImage(inputImage, (h - w) >> 1, 0, null);
        } else {
            thumbnail = new BufferedImage(w, w, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = thumbnail.createGraphics();
            g2d.drawImage(inputImage, 0, (w - h) >> 1, null);
        }
        return thumbnail;
    }

    private void getThumbPath(String imgFile) {
        // Desired thumbnail dimensions
        int thumbnailWidth = 160;
        int thumbnailHeight = 160;

        try {
            // Read the input image

            BufferedImage inputImage = getSquareCropped(imgFile);

            // Create a scaled version (thumbnail) of the image
            BufferedImage thumbnail = new BufferedImage(thumbnailWidth, thumbnailHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = thumbnail.createGraphics();
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, thumbnailWidth, thumbnailHeight, 20, 20);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Set the clip of the Graphics2D context to the round rectangle
            g2d.setClip(roundedRectangle);
            // Fill the clipped area with a transparent color
            g2d.setColor(new Color(0, 0, 0, 0)); // Transparent color
            g2d.fillRect(0, 0, thumbnailWidth, thumbnailHeight);

            g2d.drawImage(inputImage, 0, 0, thumbnailWidth, thumbnailHeight, null);
            g2d.dispose();

            // Save the thumbnail to the output file
            File outputFile = new File(PATH, "thumb");
            ImageIO.write(thumbnail, "png", outputFile);

        } catch (Exception ignored) {
        }

    }

    private boolean canThumb(File f) {
        return f.getName().endsWith("png") || f.getName().endsWith("jpg");
    }

    public Repo(RepoType type) {
        if (type == RepoType.IMAGE) path = PATH + "images/";
        else if (type == RepoType.VIDEO) path = PATH + "videos/";
        else path = PATH + "others/";
    }

    public List<String> getAll() {
        File folder = new File(path);
        var t = folder.list();
        if (t != null)
            return Arrays.stream(t).toList();
        return new ArrayList<>();
    }

    public boolean saveFile(File f) {
        var name = getRandom();
        File folEnc = new File(path + name);
        folEnc.mkdirs();

        var fileEnc = new File(folEnc, ENC_NAME);
        var fileThumb = new File(folEnc, THUMB_NAME);
        if (path.contains("images") && canThumb(f)) {
            getThumbPath(f.getAbsolutePath());

            Cipher.encFile(f, fileEnc);
            Cipher.encFile(new File(PATH, "thumb"), fileThumb);
            return true;

        } else {
            Cipher.encFile(f, fileEnc);
            return false;
        }
    }

    public BufferedImage getThumb(File file) {
        File f = new File(PATH, "decThumb");
        Cipher.decFile(file, f);
        try {
            return ImageIO.read(f);
        } catch (Exception ignored) {
        }
        return null;
    }
}
