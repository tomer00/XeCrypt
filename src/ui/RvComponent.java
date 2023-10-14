package ui;

import repo.Repo;
import utils.Rect;

import javax.swing.ImageIcon;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RvComponent {

    private final OnRVClickLis lis;
    private final List<RvElement> elements = new ArrayList<>();
    private int scrollOffset = 0;

    private final Repo repo = new Repo();
    private FontMetrics fm;

    private int off = 10, col = 5;

    private final Image videoIcon, imageIcon, othersIcon;

    public RvComponent(OnRVClickLis lis, FontMetrics fm) {
        this.lis = lis;
        videoIcon = new ImageIcon(Repo.PATH + "video.png").getImage();
        imageIcon = new ImageIcon(Repo.PATH + "image.png").getImage();
        othersIcon = new ImageIcon(Repo.PATH + "others.png").getImage();
        this.fm = fm;
    }


    //region COMMUNICATION

    public void draw(Graphics2D g) {
        g.translate(160, scrollOffset);
        for (var e : elements) e.draw(g);
        g.translate(-160, -scrollOffset);
    }

    public void onMouseMotion(int x, int y) {

    }

    public void onMouseClick(int x, int y) {

    }

    public void onMouseWheel(boolean isUp) {

    }

    public void updateRv(List<File> files) {
        elements.clear();
        int i = 0, j = 0;
        for (var f : files) {
            if (i == col) {
                i = 0;
                j++;
            }
            Image icon = repo.getThumb(f);
            if (icon == null) {
                var s = Repo.getMapHash().getOrDefault(Repo.getExt(f.getName()), "other");
                if (s.equals("images/")) icon = imageIcon;
                else if (s.equals("videos/")) {
                    icon = videoIcon;
                } else icon = othersIcon;
            }
            int x = off + (i * 130);
            int y = j * 190;
            elements.add(new RvElement(icon, f.getName(), new Rect(x, y, x + 120, y + 180), fm));
            i++;
        }
    }

    public void setOffAndColumns(int off, int col) {
        this.off = off;
        this.col = col;
    }


    public interface OnRVClickLis {
        void onclick(int pos, int type);
    }
    //endregion COMMUNICATION
}
