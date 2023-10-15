package ui;

import utils.Rect;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;

public class RvElement {
    public final ComponentState state;
    public final Image icon;
    private final String text;
    private int stringX = 0;
    private final Color lightBg = Color.decode("#dcdee0");
    private final Color darkBg = Color.decode("#9399a3");

    public final Rect decRec,delRect;

    public RvElement(Image img, String text, Rect rect, FontMetrics fm) {
        icon = img;
        state = new ComponentState(rect);
        int ind = findStr(text, fm);
        text = text.substring(0, ind);
        stringX = ((100 - fm.stringWidth(text)) >> 1) + 10;
        this.text = text;
        decRec = new Rect(rect.left + 10, rect.top + 138,rect.left + 110, rect.top + 170);
        delRect = new Rect(state.rect.left + 78, state.rect.top + 140,state.rect.left + 100, state.rect.top + 168);
    }

    public void draw(Graphics2D g) {

        g.setColor(lightBg);
        if (state.isHoverd)
            g.setColor(darkBg);

        g.fillRoundRect(state.rect.left, state.rect.top, 120, 180, 20, 20);
        g.drawImage(icon, state.rect.left + 10, state.rect.top + 10, null);
        g.setColor(Color.BLACK);
        g.drawString(text, state.rect.left + stringX, state.rect.top + 132);

        g.setColor(Color.CYAN);
        g.fillRoundRect(state.rect.left + 10, state.rect.top + 138, 100, 32, 32, 32);
        g.setColor(Color.BLACK);
        g.drawString("Decypt", state.rect.left + 20, state.rect.top + 158);

        g.setColor(Color.red);
        g.fillOval(state.rect.left + 78, state.rect.top + 140, 28, 28);
        g.setColor(Color.WHITE);
        g.drawLine(state.rect.left + 86, state.rect.top + 148, state.rect.left + 98, state.rect.top + 160);
        g.drawLine(state.rect.left + 98, state.rect.top + 148, state.rect.left + 86, state.rect.top + 160);
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
