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
    private int scrollOffset = 10;

    private final Repo repo = new Repo();
    private final FontMetrics fm;

    private int off = 10, col = 5, yMax = 0;
    private final Rect rect;

    private final Image videoIcon, imageIcon, othersIcon;
    private final MainView mv;
    private int lastHovered = 0;


    public RvComponent(MainView parent, Rect rect, OnRVClickLis lis, FontMetrics fm) {
        this.lis = lis;
        videoIcon = new ImageIcon(Repo.PATH + "video.png").getImage();
        imageIcon = new ImageIcon(Repo.PATH + "image.png").getImage();
        othersIcon = new ImageIcon(Repo.PATH + "others.png").getImage();
        this.fm = fm;
        this.rect = rect;
        mv = parent;
    }


    //region COMMUNICATION

    public void draw(Graphics2D g) {
        g.translate(160, scrollOffset);
        for (var e : elements) e.draw(g);
        g.translate(-160, -scrollOffset);
    }

    public void onMouseMotion(int x, int y) {
        int n = elements.size();
        boolean isAny = false;
        for (int i = 0; i < n; i++) {
            if (elements.get(i).state.rect.contains(x - 160, y - scrollOffset)) {
                if (i == lastHovered) return;
                if (lastHovered != -1) elements.get(lastHovered).state.isHoverd = false;
                elements.get(i).state.isHoverd = true;
                lastHovered = i;
                isAny = true;
                break;
            }
        }
        if (isAny) {
            mv.repaint();
        } else {
            if (lastHovered != -1) {
                elements.forEach((e) -> e.state.isHoverd = false);
                mv.repaint();
                lastHovered = -1;
            }
        }
    }

    public void onMouseClick(int x, int y) {
        int n = elements.size();
        for (int i = 0; i < n; i++) {
            var e = elements.get(i);
            if (e.state.rect.contains(x - 160, y - scrollOffset)) {
                if (e.delRect.contains(x - 160, y - scrollOffset)) lis.onclick(i, 2);
                else if (e.decRec.contains(x - 160, y - scrollOffset)) lis.onclick(i, 1);
                else {
                    if (elements.get(i).icon == imageIcon || elements.get(i).icon == videoIcon || elements.get(i).icon == othersIcon)
                        return;
                    lis.onclick(i, 0);
                }
                return;
            }
        }
    }

    public void onMouseWheel(boolean isUp) {
        if (yMax < rect.height()) return;
        if (!isUp) {
            scrollOffset -= 20;
            if (-scrollOffset > yMax - rect.height()) scrollOffset = -(yMax - rect.height() + 10);
        } else {
            scrollOffset += 20;
            if (scrollOffset > 10) scrollOffset = 10;
        }
    }

    public void updateRv(List<File> files) {
        elements.clear();
        yMax = 180;
        scrollOffset = 10;
        int i = 0, j = 0;
        for (var f : files) {
            if (i == col) {
                i = 0;
                j++;
                yMax += 190;
            }
            Image icon = repo.getThumb(f);
            var name = repo.getName(f);
            if (icon == null) {
                var s = Repo.getMapHash().getOrDefault(Repo.getExt(name), "other");
                if (s.equals("images/")) icon = imageIcon;
                else if (s.equals("videos/")) {
                    icon = videoIcon;
                } else icon = othersIcon;
            }
            int x = off + (i * 130);
            int y = j * 190;
            elements.add(new RvElement(icon, name, new Rect(x, y, x + 120, y + 180), fm));
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
