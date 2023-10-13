package repo;

import cipher.CipherUtils;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Repo {
    public static final String PATH = "/home/tom/test/xcrypt/";
    public static final String ENC_NAME = "dHF0pywK8FvtBOQ27RoezfsXuKRhaybM";
    public static final String THUMB_NAME = "YrjmD2Dt97ambqy4GUeStRgy3AODGCPp";
    public static final String META_NAME = "StRgy3AODGCPp2Dt97ambqy4GUeYrjmD";

    //region STATIC BLOCK

    private static String READIED_HASH;

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
        if (READIED_HASH == null) {
            File fHash = new File(PATH + "hash");
            try (var ins = new BufferedReader(new InputStreamReader(new FileInputStream(fHash)))) {
                READIED_HASH = ins.readLine();
            } catch (Exception ignored) {
                READIED_HASH = "";
            }
        }
        return READIED_HASH;
    }


    //endregion STATIC BLOCK

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
        int thumbnailWidth = 100;
        int thumbnailHeight = 100;

        try {

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

    //region FILE TYPE MAPPING


    private static HashMap<String, String> mapTypes;

    private static HashMap<String, String> getMapHash() {
        if (mapTypes == null) {
            mapTypes = new HashMap<>();

            mapTypes.put("jpg", "images/");
            mapTypes.put("jpeg", "images/");
            mapTypes.put("png", "images/");
            mapTypes.put("webp", "images/");
            mapTypes.put("gif", "images/");
            mapTypes.put("svg", "images/");
            mapTypes.put("bmp", "images/");
            mapTypes.put("ico", "images/");
            mapTypes.put("tif", "images/");
            mapTypes.put("psd", "images/");

            mapTypes.put("mp4", "videos/");
            mapTypes.put("mkv", "videos/");
            mapTypes.put("mov", "videos/");
            mapTypes.put("wmv", "videos/");
            mapTypes.put("flv", "videos/");
            mapTypes.put("avi", "videos/");
            mapTypes.put("webm", "videos/");

        }
        return mapTypes;
    }

    //endregion FILE TYPE MAPPING

    private String subDir(String ext) {
        return getMapHash().getOrDefault(ext, "others/");
    }

    private String getExt(String name) {
        int index = name.lastIndexOf('.');
        if (index > -1)
            return name.substring(index + 1);
        return "";
    }

    public void decFile(File f) {
        File out = new File(PATH, "cache");
        if (!out.exists())
            out.mkdirs();

        File inpFile = new File(f, ENC_NAME);
        out = new File(out, f.getName());

        CipherUtils.decFile(inpFile, out);

    }

    public List<String> getAll(String path) {
        File folder = new File(PATH, path);
        var t = folder.list();
        if (t != null)
            return Arrays.stream(t).toList();
        return new ArrayList<>();
    }

    public void saveFile(File f) {
        var path = PATH + subDir(getExt(f.getName()));
        var name = getRandom();
        File folEnc = new File(path + name);
        folEnc.mkdirs();

        var fileEnc = new File(folEnc, ENC_NAME);
        var fileThumb = new File(folEnc, THUMB_NAME);
        var fileMeta = new File(folEnc, META_NAME);
        try (var out = new FileOutputStream(fileMeta)) {
            out.write(CipherUtils.encrypt(f.getName()).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException ignored) {
        }
        if (path.contains("images") && canThumb(f)) {
            getThumbPath(f.getAbsolutePath());

            File thumb = new File(PATH, "thumb");
            CipherUtils.encFile(f, fileEnc);
            CipherUtils.encFile(thumb, fileThumb);
            thumb.deleteOnExit();

        } else {
            CipherUtils.encFile(f, fileEnc);
        }
    }

    public BufferedImage getThumb(File file) {
        File f = new File(PATH, "decThumb");
        CipherUtils.decFile(file, f);
        f.deleteOnExit();
        try {
            return ImageIO.read(f);
        } catch (Exception ignored) {
        }
        return null;
    }
}
