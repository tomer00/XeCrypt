package ui;

import utils.Rect;

import java.awt.Graphics2D;

public abstract class View {

    protected final Rect rect;
    private boolean isVisi = true;

    public View(Rect r) {
        this.rect = r;
    }

    abstract void draw(Graphics2D g);

    protected void setVisibility(boolean isVisi) {
        this.isVisi = isVisi;
    }

    protected boolean getVisibility() {
        return isVisi;
    }

    protected void setRect(Rect r) {
        this.rect.set(r);
    }
}
