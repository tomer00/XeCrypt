package ui;

import repo.Repo;
import utils.Rect;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainView extends JComponent {

    private final Image imgLogo = new ImageIcon(Repo.PATH + "xcryLogo.png").getImage();
    private final Image imgDrop = new ImageIcon(Repo.PATH + "drop.png").getImage();
    private final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private final Rect rectSide = new Rect(0, 0, 0, 0);
    private final Rect rectMain = new Rect(0, 0, 0, 0);
    private int prevW = 0, prevH = 0;

    //side Panel
    private final ComponentState[] sideButtons = new ComponentState[3];
    private GradientPaint graSide;

    private final RvComponent rvComponent;

    private boolean isDragIn = false;

    private final Font fontSide = new Font("Uroob", Font.BOLD, 30);
    private final Font fontMain = new Font("System", Font.PLAIN, 16);

    public MainView() {
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);


        sideButtons[0] = new ComponentState(new Rect(20, 20, 140, 60));
        sideButtons[1] = new ComponentState(new Rect(20, 20, 140, 60));
        sideButtons[2] = new ComponentState(new Rect(20, 20, 140, 60));

        sideButtons[0].isSelected = true;

        rvComponent = new RvComponent((pos, type) -> {

        }, getFontMetrics(fontMain));

        //region MOUSE LISTENERS


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int x = e.getX();
                int y = e.getY();

                if (rectMain.contains(x, y)) {
                    rvComponent.onMouseClick(x, y);
                } else if (rectSide.contains(x, y)) {
                    for (var sb : sideButtons) {
                        if (sb.rect.contains(x, y)) {
                            for (var sb2 : sideButtons) sb2.isSelected = false;
                            sb.isSelected = true;
                            repaint();
                            return;
                        }
                    }
                }
            }
        });

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                if (rectMain.contains(e.getX(), e.getY())) rvComponent.onMouseWheel(e.getWheelRotation() == -1);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                int x = e.getX();
                int y = e.getY();

                if (rectMain.contains(x, y)) {
                    rvComponent.onMouseMotion(x, y);
                } else if (rectSide.contains(x, y)) {
                    for (var sb : sideButtons) {
                        if (sb.rect.contains(x, y)) {
                            for (var sb2 : sideButtons) sb2.isHoverd = false;
                            sb.isHoverd = true;
                            repaint();
                            return;
                        }
                    }
                    for (var sb : sideButtons)
                        sb.isHoverd = false;
                    repaint();
                }
            }
        });

        //endregion MOUSE LISTENERS


        var files = Arrays.stream(new File(Repo.PATH + "images").listFiles()).collect(Collectors.toList());
        rvComponent.updateRv(files);

    }

    @Override
    public void paint(Graphics _g) {
        Graphics2D g = (Graphics2D) _g.create();

        g.setFont(fontSide);
        if (prevW != getWidth() || prevH != getHeight()) {
            rectSide.set(new Rect(0, 0, 160, getHeight()));
            rectMain.set(new Rect(160, 0, getWidth(), getHeight()));

            int sY = (getHeight() - 280) >> 1;
            sY += 120;
            sideButtons[0].rect.set(new Rect(10, sY, 140, sY + 40));
            sideButtons[1].rect.set(new Rect(10, sY + 60, 140, sY + 100));
            sideButtons[2].rect.set(new Rect(10, sY + 120, 140, sY + 160));

            graSide = new GradientPaint(0, 0, Color.RED, 160, getHeight(), Color.YELLOW);


            prevH = getHeight();
            prevW = getWidth();

            int w = getWidth() - 160;

            int col =( getWidth()-60) / 130;
            col--;
            int off = (w - ((col * 130) -10)) >> 1;

            rvComponent.setOffAndColumns(off, col);
            rvComponent.updateRv(Arrays.stream(new File(Repo.PATH + "images").listFiles()).collect(Collectors.toList()));
        }

        g.setRenderingHints(hints);


        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(rectSide.left, rectSide.top, rectSide.width(), rectSide.height());

        g.setColor(new Color(239, 234, 234));
        g.fillRect(rectMain.left, rectMain.top, rectMain.width(), rectMain.height());


        //region SIDE PANEL

        g.setPaint(graSide);
        g.fillRect(0, 0, rectSide.width(), rectSide.height());

        g.drawImage(imgLogo, 30, 20, 100, 100, null);


        g.setColor(Color.WHITE);
        for (var sb : sideButtons) {
            if (sb.isHoverd) {
                g.fillRoundRect(sb.rect.left, sb.rect.top, sb.rect.width(), sb.rect.height(), 40, 40);
                break;
            }
        }

        g.setColor(Color.CYAN);
        for (var sb : sideButtons) {
            if (sb.isSelected) {
                g.fillRoundRect(sb.rect.left, sb.rect.top, sb.rect.width(), sb.rect.height(), 40, 40);
                break;
            }
        }

        g.setColor(Color.BLACK);
        g.drawString("IMAGES", 43, sideButtons[0].rect.bottom - 10);
        g.drawString("VIDEOS", 44, sideButtons[1].rect.bottom - 10);
        g.drawString("OTHERS", 40, sideButtons[2].rect.bottom - 10);

        //endregion SIDE PANEL

        g.setFont(fontMain);

        rvComponent.draw(g);

        g.setFont(fontSide);

        if (!isDragIn) return;
        g.setColor(new Color(157, 152, 152, 180));
        g.fillRect(160, 0, getWidth(), getHeight());

        int y = (rectMain.height() - 380) >> 1;
        g.drawImage(imgDrop, ((rectMain.width() - 288) >> 1) + 160, y, null);
        g.setColor(Color.BLACK);
        g.drawString("Drop Files to Encrypt", ((rectMain.width() - getFontMetrics(fontSide).stringWidth("Drop Files to Encrypt")) >> 1) + 160, y + 300);

    }

    //region COMMUNICATION

    public void dropEvent(boolean draggedIn) {
        isDragIn = draggedIn;
        repaint();
    }

    public void encrypting(List<File> files) {

    }
    //endregion COMMUNICATION


}
