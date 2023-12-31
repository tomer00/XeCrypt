package repo;

import cipher.CipherUtils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Random;

public class Repo {
    public static String ASSETS = "xcrypt/";
    public static String PATH_X = "xcry/";
    public static final String ENC_NAME = "dHF0pywK8FvtBOQ27RoezfsXuKRhaybM";
    public static final String THUMB_NAME = "YrjmD2Dt97ambqy4GUeStRgy3AODGCPp";
    public static final String META_NAME = "StRgy3AODGCPp2Dt97ambqy4GUeYrjmD";

    //region STATIC BLOCK

    private static String READIED_HASH;

    public static void saveHash(String pass) {
        File fHash = new File(ASSETS + "hash");
        try (var ois = new FileOutputStream(fHash)) {
            ois.write(pass.getBytes(StandardCharsets.UTF_8));
            ois.flush();
            READIED_HASH = pass;
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
        if (READIED_HASH == null || READIED_HASH.isEmpty()) {
            File fHash = new File(ASSETS + "hash");
            try (var ins = new BufferedReader(new InputStreamReader(new FileInputStream(fHash)))) {
                READIED_HASH = ins.readLine();
            } catch (Exception ignored) {
                READIED_HASH = "";
            }
        }
        return READIED_HASH == null ? "" : READIED_HASH;
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
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            // Set the clip of the Graphics2D context to the round rectangle
            g2d.setClip(roundedRectangle);
            // Fill the clipped area with a transparent color
            g2d.setColor(new Color(0, 0, 0, 0)); // Transparent color
            g2d.fillRect(0, 0, thumbnailWidth, thumbnailHeight);

            g2d.drawImage(inputImage, 0, 0, thumbnailWidth, thumbnailHeight, null);
            g2d.dispose();

            // Save the thumbnail to the output file
            File outputFile = new File(ASSETS, "thumb");
            ImageIO.write(thumbnail, "png", outputFile);

        } catch (Exception ignored) {
        }

    }

    private boolean canThumb(File f) {
        return f.getName().endsWith("png") || f.getName().endsWith("jpg");
    }

    //region FILE TYPE MAPPING


    private static HashMap<String, String> mapTypes;

    public static HashMap<String, String> getMapHash() {
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

    public static String getExt(String name) {
        int index = name.lastIndexOf('.');
        if (index > -1)
            return name.substring(index + 1);
        return "";
    }

    public void decFile(File f, boolean isTemp) {
        File inpFile = new File(f, ENC_NAME);
        File out = new File(PATH_X, ".cache");
        if (!out.exists()) {
            out.mkdirs();
        }
        out = new File(out, getName(f));


        if (isTemp)
            out = new File(ASSETS, "lastCache");
        CipherUtils.decFile(inpFile, out);
    }

    public String getName(File fol) {
        File f = new File(fol, META_NAME);
        try (var b = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
            return CipherUtils.decrypt(b.readLine());
        } catch (Exception e) {
            return "";
        }
    }

    public void saveFile(File f) {
        var path = PATH_X + subDir(getExt(f.getName()));
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

            File thumb = new File(ASSETS, "thumb");
            CipherUtils.encFile(f, fileEnc);
            CipherUtils.encFile(thumb, fileThumb);
            thumb.deleteOnExit();

        } else {
            CipherUtils.encFile(f, fileEnc);
        }
    }

    public Image getThumb(File file) {
        File fileThumb = new File(file, THUMB_NAME);
        if (!fileThumb.exists()) return null;

        File cachedThumb = new File(PATH_X + ".thumbs", file.getName());

        if (cachedThumb.exists())
            return new ImageIcon(cachedThumb.getAbsolutePath()).getImage();

        File f = new File(PATH_X + ".thumbs", file.getName());
        f.getParentFile().mkdirs();
        CipherUtils.decFile(fileThumb, f);
        try {
            return ImageIO.read(f);
        } catch (Exception ignored) {
        }
        return null;
    }
}
