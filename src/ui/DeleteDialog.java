package ui;

import repo.Repo;
import utils.Rect;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class DeleteDialog extends JFrame {

    private final File f;
    private final DelDia.DelLis lis;


    public DeleteDialog(File f, DelDia.DelLis l, String name) {
        this.f = f;
        lis = l;
        this.setSize(220, 360);
        this.setUndecorated(true);
        this.setShape(new RoundRectangle2D.Float(0, 0, 220, 360, 40, 40));
        this.setIconImage(new ImageIcon(Repo.ASSETS + "delIcon.png").getImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        this.add(new DelDia(this::del, name));
    }

    void del(boolean isDel) {
        if (isDel) {
            for (File subfile : Objects.requireNonNull(f.listFiles())) subfile.delete();
            f.delete();
            this.dispose();
            lis.onDel(true);
        } else this.dispose();
    }
}

class DelDia extends JComponent {
    private final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private final Image img;
    private boolean isHovered = false;

    private final Stroke st = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private final Font f = new Font("System", Font.BOLD, 20);
    private final Rect rectDel, rectClose;
    private final String fileName;

    private final Font smallFont;
    private final ArrayList<String> lines;

    public DelDia(DelLis lis, String fileName) {
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        img = new ImageIcon(Repo.ASSETS + "delIcon.png").getImage();
        this.fileName = fileName;

        rectDel = new Rect(20, 312, 140, 344);
        rectClose = new Rect(160, 312, 192, 344);

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (e.getY() < 300) return;
                if (rectDel.contains(e.getX(), e.getY())) {
                    if (!isHovered) {
                        isHovered = true;
                        repaint();
                    }
                } else {
                    if (isHovered) {
                        isHovered = false;
                        repaint();
                    }
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getY() < 310) return;
                if (rectDel.contains(e.getX(), e.getY())) lis.onDel(true);
                if (rectClose.contains(e.getX(), e.getY())) lis.onDel(false);
            }
        });

        smallFont = f.deriveFont(16f);
        lines = lines(getFontMetrics(smallFont));
    }

    @Override
    public void paint(Graphics _g) {
        Graphics2D g = (Graphics2D) _g.create();
        g.setRenderingHints(hints);
        g.setFont(f);

        g.setColor(Color.red);

        g.fillOval(-90, -160, 400, 320);
        g.drawImage(img, 50, 18, null);

        if (isHovered)
            g.setColor(Color.BLACK);
        g.fillRoundRect(20, 312, 120, 32, 32, 32);

        g.setStroke(st);

        g.setColor(Color.BLACK);
        g.fillOval(160, 312, 32, 32);
        g.setColor(Color.WHITE);
        g.drawLine(170, 322, 182, 334);
        g.drawLine(182, 322, 170, 334);

        g.drawString("DELETE", 38, 335);

        g.setColor(Color.BLACK);
        g.setFont(smallFont);
        g.drawString("Confirm Delete", 48, 180);

        AtomicInteger y= new AtomicInteger(220);
        lines.forEach((t) -> {
            g.drawString(t,10, y.get());
            y.addAndGet(20);
        });

    }

    private ArrayList<String> lines(FontMetrics fm) {
        ArrayList<String> li = new ArrayList<>();

        var n = fileName.length();
        if (n < 20) {
            li.add(fileName);
            return li;
        }
        char[] chars = fileName.toCharArray();
        int off = 0, l = 1;

        for (int i = 0; i < n; i++) {
            if (fm.charsWidth(chars, off, l) > 200) {
                li.add(String.copyValueOf(chars, off, --l));
                off += l;
                l = 1;
            } else l++;
        }
        li.add(String.copyValueOf(chars, off, n - off));
        return li;

    }


    public interface DelLis {
        void onDel(boolean b);
    }
}