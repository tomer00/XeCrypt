package ui;

import utils.CharHandler;
import utils.Rect;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class EditText {

    public final Rect rect;
    private final CharHandler charHandler;
    private final CharHandler charHandlerVisi;
    public boolean isVisible = false;
    public boolean error = false;

    private final Color colBg = Color.decode("#ae3d71");

    public EditText(Rect r, FontMetrics fm) {
        rect = r;
        charHandler = new CharHandler("", fm);
        charHandlerVisi = new CharHandler("", fm);
    }

    void draw(Graphics2D g) {

        if (error) {
            g.setColor(Color.RED);
            g.drawString("Password Incorrect..", 12, 154);
        }

        g.setColor(colBg);
        g.fillRoundRect(rect.left, rect.top, rect.width(), rect.height(), 10, 10);
        g.setColor(Color.WHITE);
        int x = (220 - (isVisible ? charHandlerVisi.width : charHandler.width)) >> 1;
        if (isVisible)
            g.drawString(charHandlerVisi.getText(), x, 180);
        else
            g.drawString(charHandler.getText(), x, 180);

        g.fillRect(x + (isVisible ? charHandlerVisi.width : charHandler.width) + 2, 164, 3, 20);

    }

    public String getPass() {
        return charHandlerVisi.getText();
    }

    public void append(char c) {
        if (charHandler.length > 10) return;
        charHandler.append('*');
        charHandlerVisi.append(c);
    }

    public void del() {
        charHandler.delete();
        charHandlerVisi.delete();
    }
}
