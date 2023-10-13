package ui;

import utils.Rect;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;

public class RvElement {
    public final ComponentState state;
    private final Image icon;
    private final String text;
    private int stringX = 0;

    public RvElement(Image img, String text, Rect rect, FontMetrics fm) {
        icon = img;
        state = new ComponentState(rect);
        int ind = findStr(text, fm);
        text = text.substring(0, ind);
        stringX = (fm.stringWidth(text) - 100) >> 1;
        this.text = text;
        state.isHoverd = true;
    }

    public void draw(Graphics2D g) {

        if (state.isHoverd) {
            g.setColor(Color.GRAY);
            g.fillRoundRect(state.rect.left, state.rect.top, 120, 180, 20, 20);
        }
        g.drawImage(icon, state.rect.left + 10, state.rect.top + 10, null);
        g.setColor(Color.BLACK);
        g.drawString(text, stringX, 132);

        g.setColor(Color.CYAN);
        g.fillRoundRect(state.rect.left + 10, state.rect.top + 138, 100, 32, 32, 32);
    }

    private int findStr(String str, FontMetrics fm) {
        int i = 0, j = str.length() - 1, mid = 0;
        while (i <= j) {
            mid = i + (j - i) / 2;
            if (fm.stringWidth(str.substring(0, mid)) > 100) j--;
            else i++;
        }
        return mid;
    }
}
