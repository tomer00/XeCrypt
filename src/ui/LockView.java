package ui;

import repo.Repo;
import utils.HashingUtils;
import utils.Rect;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.*;

public class LockView extends JComponent {

    private final Image imgLogo;
    private final EditText et;
    private final Rect[] listButtonsRect = new Rect[12];
    private final boolean[] listIsHovered = new boolean[12];
    private final Image[] listImages = new Image[12];

    private final Color colPrev = Color.decode("#fcfcfc");
    private final Color colHovered = Color.decode("#e63371");
    private final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private int lastIndex = 0;
    private final Rect rectEye = new Rect(175, 165, 195, 185);
    private final Image eyeVisi, eyeHidden;

    private final Font font = new Font("ubuntu", Font.BOLD, 20);
    private final boolean isFirst;
    private final CloseCallback root;

    public LockView(boolean isFirst, CloseCallback callback) {

        //region ASSETS INIT
        this.isFirst = isFirst;
        this.root = callback;

        imgLogo = new ImageIcon(Repo.PATH + "xcryLogo.png").getImage();
        eyeHidden = new ImageIcon(Repo.PATH + "eyeHidden.png").getImage();
        eyeVisi = new ImageIcon(Repo.PATH + "eyeVisi.png").getImage();
        et = new EditText(new Rect(20, 160, 200, 190), getFontMetrics(font));
        for (int i = 0; i < 10; i++) {
            listIsHovered[i] = false;
            listImages[i] = new ImageIcon(Repo.PATH + i + ".png").getImage();
        }
        listIsHovered[10] = false;
        listIsHovered[11] = false;

        listImages[10] = new ImageIcon(Repo.PATH + "back.png").getImage();
        listImages[11] = new ImageIcon(Repo.PATH + "next.png").getImage();

        listButtonsRect[1] = new Rect(10, 200, 70, 240);
        listButtonsRect[2] = new Rect(80, 200, 140, 240);
        listButtonsRect[3] = new Rect(150, 200, 210, 240);

        listButtonsRect[4] = new Rect(10, 250, 70, 290);
        listButtonsRect[5] = new Rect(80, 250, 140, 290);
        listButtonsRect[6] = new Rect(150, 250, 210, 290);

        listButtonsRect[7] = new Rect(10, 300, 70, 340);
        listButtonsRect[8] = new Rect(80, 300, 140, 340);
        listButtonsRect[9] = new Rect(150, 300, 210, 340);

        listButtonsRect[10] = new Rect(10, 350, 70, 390);
        listButtonsRect[0] = new Rect(80, 350, 140, 390);
        listButtonsRect[11] = new Rect(150, 350, 210, 390);

        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        //endregion ASSETS INIT

        //region MOUSE LISTENERS


        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                if (e.getY() > 190) {
                    boolean any = false;
                    for (int i = 0; i < 12; i++) {
                        if (listButtonsRect[i].contains(e.getX(), e.getY())) {
                            listIsHovered[i] = true;
                            if (lastIndex != i)
                                listIsHovered[lastIndex] = false;
                            lastIndex = i;
                            any = true;
                            break;
                        }
                    }
                    if (!any) listIsHovered[lastIndex] = false;
                    repaint();
                }

            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                listIsHovered[lastIndex] = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getY() > 200) {
                    for (int i = 0; i < 12; i++) {
                        if (listButtonsRect[i].contains(e.getX(), e.getY())) {
                            if (i < 10) {
                                if (et.error) et.error = false;
                                et.append((char) ('0' + i));
                            } else if (i == 10) et.del();
                            else nextScr();
                            repaint();
                            break;
                        }
                    }
                } else if (rectEye.contains(e.getX(), e.getY())) {
                    et.isVisible = !et.isVisible;
                    repaint();
                }

            }
        });
        //endregion MOUSE LISTENERS

        //region KEYLIS

        this.setFocusable(true);
        this.grabFocus();

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    et.del();
                    if (et.error) et.error = false;
                    repaint();
                } else if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == '.') {
                    et.append(e.getKeyChar());
                    if (et.error) et.error = false;
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nextScr();
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //endregion KEYLIS

    }

    private void nextScr() {
        if (isFirst) {
            Repo.saveHash(HashingUtils.get_SHA_1_SecurePassword(et.getPass()));
            callNextActivity();
            return;
        }

        if (Repo.getHash().contentEquals(HashingUtils.get_SHA_1_SecurePassword(et.getPass())))
            callNextActivity();
        else et.error = true;
    }

    private void callNextActivity() {
        root.onClose();
    }


    @Override
    public void paint(Graphics _g) {
        Graphics2D g = (Graphics2D) _g;

        g.setRenderingHints(hints);
        g.drawImage(imgLogo, 50, 10, 120, 120, null);
        g.setFont(font);
        if (isFirst) {
            g.setColor(Color.RED);
            g.drawString("Create a Password", 18, 154);
        }
        et.draw(g);

        g.fillOval(rectEye.left, rectEye.top, rectEye.width(), rectEye.height());
        g.drawImage(et.isVisible ? eyeVisi : eyeHidden, rectEye.left, rectEye.top, rectEye.width(), rectEye.height(), null);

        g.setColor(colPrev);
        //base
        for (int i = 0; i < 12; i++) {
            var r = listButtonsRect[i];
            g.fillRoundRect(r.left, r.top, r.width(), r.height(), 10, 10);
            if (listIsHovered[i]) {
                g.setColor(colHovered);
                g.fillRoundRect(r.left, r.top, r.width(), r.height(), 10, 10);
                g.setColor(colPrev);
            }
            g.drawImage(listImages[i], r.left, r.top, r.width(), r.height(), null);
        }

    }

    public interface CloseCallback {
        void onClose();
    }
}
