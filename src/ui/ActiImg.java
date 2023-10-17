package ui;

import repo.Repo;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ActiImg extends JFrame {

    ActiImg(String f) {

        this.setIconImage(new ImageIcon(Repo.ASSETS + "image.png").getImage());
        this.setTitle(f);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setSize(600, 400);

        this.setLayout(new GridLayout(1, 1));

        this.add(new ImageView(new File(Repo.ASSETS, "lastCache")));
    }
}


class ImageView extends JComponent {
    private final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private BufferedImage img;
    private final Color col1 = Color.decode("#abecd6");
    private final Color col2 = Color.decode("#fbed96");


    public ImageView(File f) {
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        try {
            img = ImageIO.read(f);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void paint(Graphics _g) {
        Graphics2D g = (Graphics2D) _g.create();
        g.setRenderingHints(hints);

        g.setPaint(new GradientPaint(0, getHeight(), col1, getWidth(), 0, col2, false));
        g.fillRect(0, 0, getWidth(), getHeight());

        int x = 0;
        int y = 0;
        int w = img.getWidth();
        int h = img.getHeight();

        if (getWidth() > getHeight()) {
            if (w > h) {
                h = (int) (h * ((float) getWidth() / w));
                w = getWidth();
                y = (getHeight() - h) >> 1;
            } else {
                w = (int) (w * ((float) getHeight() / h));
                x = (getWidth() - w) >> 1;
                h = getHeight();
            }
        } else {
            if (w > h) {
                w = (int) (w * ((float) getHeight() / h));
                x = (getWidth() - w) >> 1;
                h = getHeight();
            } else {
                h = (int) (h * ((float) getWidth() / w));
                w = getWidth();
                y = (getHeight() - h) >> 1;
            }
        }

        g.drawImage(img, x, y, w, h, null);
    }
}