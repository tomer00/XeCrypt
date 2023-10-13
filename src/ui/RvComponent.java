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

    private final Image videoIcon, imageIcon, othersIcon;

    public RvComponent(OnRVClickLis lis,FontMetrics fm) {
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
        for (var f : files) {
            elements.add(new RvElement(videoIcon, "HII texst jfvbsdjbsdbsbvjsdhjdfs", new Rect(0, 0, 120, 180), fm));
        }
    }

    public interface OnRVClickLis {
        void onclick(int pos, int type);
    }
    //endregion COMMUNICATION
}
