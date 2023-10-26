package ui;

import repo.Repo;
import utils.GifDecoder;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class EncDecActi extends JFrame {

    private final List<String> files = new ArrayList<>();
    private ImageViewGif gifView;
    private final boolean isEnc;

    public EncDecActi(boolean isEnc, List<File> files) {
        this.setIconImage(new ImageIcon(Repo.ASSETS + "xcryLogo.png").getImage());
        this.setTitle(files.size() + " Files");
        this.setVisible(true);
        this.setSize(220, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.isEnc = isEnc;


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (gifView != null)
                    gifView.isConti = false;
            }
        });

        for (File file : files)
            this.files.add(file.getName());

        this.setLayout(new GridLayout(1, 1));
        gifView = new ImageViewGif(isEnc ? "Encrypting" : "Decrypting");
        this.add(gifView);
        this.add(gifView);
        this.repaint();
    }

    public void done() {
        this.remove(gifView);
        gifView.isConti = false;
        gifView = null;
        System.gc();
        if (!isEnc) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(Repo.PATH_X + ".cache"));
            } catch (Exception ignored) {
            }
            this.dispose();
            return;
        }
        this.add(new FilesView(files, (i, j) -> dispose()));
        this.setResizable(true);
        this.setSize(400, 600);
        this.setMinimumSize(new Dimension(230, 200));
    }
}

class FilesView extends JComponent {
    private final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private final List<String> files;

    private final Point rectOpen, rectClose, rectText;
    private int prevW = 0, prevH = 0;

    private boolean isShow = false, isClose = false;
    private final Color colShow = new Color(7, 159, 102);
    private GradientPaint gp;

    public FilesView(List<String> files, RvComponent.OnRVClickLis lis) {
        setFont(new Font("Uroob", Font.BOLD, 30));
        this.files = files;

        rectClose = new Point(0, 0);
        rectOpen = new Point(0, 0);
        rectText = new Point(20, 40);


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (e.getY() > getHeight() - 68) {
                    int x = e.getX();
                    int y = e.getY();
                    if (x > rectOpen.x && y > rectOpen.y && x < rectOpen.x + 110 && y < rectOpen.y + 40) {
                        isShow = true;
                        isClose = false;
                        repaint();
                        return;
                    }
                    if (x > rectClose.x && y > rectClose.y && x < rectClose.x + 110 && y < rectClose.y + 40) {
                        isShow = false;
                        isClose = true;
                        repaint();
                        return;
                    }
                    isShow = false;
                    isClose = false;
                    repaint();
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getY() > getHeight() - 60) {
                    int x = e.getX();
                    int y = e.getY();
                    if (x > rectOpen.x && y > rectOpen.y && x < rectOpen.x + 110 && y < rectOpen.y + 40) {
                        try {
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(new File(Repo.PATH_X));
                        } catch (Exception ignored) {
                        }
                    }
                    if (x > rectClose.x && y > rectClose.y && x < rectClose.x + 110 && y < rectClose.y + 40) {
                        lis.onclick(0, 0);
                    }
                }
            }
        });

    }

    @Override
    public void paint(Graphics _g) {
        Graphics2D g = (Graphics2D) _g.create();
        g.setRenderingHints(hints);

        if (prevH != getHeight() || prevW != getWidth()) {
            prevH = getHeight();
            prevW = getWidth();

            int maxWidth = 0;
            var fm = getFontMetrics(getFont());
            for (var s : files)
                maxWidth = Math.max(maxWidth, fm.stringWidth(s));


            if (maxWidth < prevW)
                rectText.setLocation((prevW - maxWidth) >> 1, 40);
            else
                rectText.setLocation(20, 40);


            int x1 = (prevW - 230) >> 1;

            rectOpen.setLocation(x1, prevH - 60);
            rectClose.setLocation(x1 + 120, prevH - 60);
            gp = new GradientPaint(rectText.x - 30, rectText.y - 30, new Color(61, 245, 167),
                    rectText.x + 250, rectText.y + 10, new Color(9, 111, 224));
        }

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());


        g.setPaint(gp);
        g.fillRoundRect(rectText.x - 30, rectText.y - 30, 220, 40, 20, 20);

        g.setColor(Color.BLACK);
        g.drawString("Encrypted " + files.size() + " files", rectText.x - 10, rectText.y);

        int j = 0;
        for (var f : files)
            g.drawString(f, rectText.x, rectText.y + (j++ * 32) + 80);


        g.setColor(Color.WHITE);
        g.fillRect(0, rectOpen.y - 4, getWidth(), getHeight() - rectOpen.y + 4);

        g.setColor(colShow);
        if (isShow)
            g.setColor(Color.BLACK);
        g.fillRoundRect(rectOpen.x, rectOpen.y, 110, 40, 40, 40);

        g.setColor(Color.RED);
        if (isClose)
            g.setColor(Color.BLACK);
        g.fillRoundRect(rectClose.x, rectClose.y, 110, 40, 40, 40);

        g.setColor(Color.WHITE);
        g.drawString("SHOW", rectOpen.x + 28, rectOpen.y + 29);
        g.drawString("CLOSE", rectClose.x + 24, rectClose.y + 29);

    }
}

class ImageViewGif extends JComponent {
    private final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private int frame = 0;

    private final GifDecoder decoder;
    public boolean isConti = true;

    private final String text;

    public ImageViewGif(String text) {
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        decoder = new GifDecoder();
        try (var ins = new FileInputStream(Repo.ASSETS + "enc.gif")) {
            decoder.read(ins);
        } catch (Exception ignored) {
        }

        setFont(new Font("Uroob", Font.BOLD, 30));
        this.text = text;

        new Thread(() -> {
            while (isConti) {
                if (frame == decoder.getFrameCount()) frame = 0;
                try {
                    Thread.sleep(decoder.getDelay(frame));
                } catch (InterruptedException ignored) {
                }
                repaint();
                frame++;
            }
        }).start();

    }

    @Override
    protected void paintComponent(Graphics _g) {
        Graphics2D g = (Graphics2D) _g;
        g.setRenderingHints(hints);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(decoder.getFrame(frame), 10, 10, 200, 200, null);

        g.setColor(Color.BLACK);
        g.drawString(text, 57, 220);
    }

}
