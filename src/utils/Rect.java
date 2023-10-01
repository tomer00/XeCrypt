package utils;

public class Rect {

    public int left, top, right, bottom;

    public Rect(int l, int t, int r, int b) {
        left = l;
        top = t;
        right = r;
        bottom = b;
    }

    public int width() {
        return right - left;
    }

    public int height() {
        return bottom - top;
    }

    public boolean contains(int x, int y) {
        return (left <= x && x <= right) && (top <= y && y <= bottom);
    }

    public void set(Rect r) {
        this.left = r.left;
        this.right = r.right;
        this.top = r.top;
        this.bottom = r.bottom;
    }
}
