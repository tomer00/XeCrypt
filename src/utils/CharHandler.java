package utils;

import java.awt.FontMetrics;

public class CharHandler {


    //region GLOBALS-------->>

    public int length = 0;
    private int capacity = 18;
    public char[] data;
    public int width = 0;

    private final FontMetrics fm;
    //endregion GLOBALS-------->>

    //region INIT->>>>

    public CharHandler(String str, FontMetrics fm) {
        data = new char[18];
        str.getChars(0, str.length(), data, 0);
        this.fm = fm;
    }

    //endregion INIT->>>>

    //region COMMUNICATION----->

    public void append(char c) {
        length++;
        if (length > capacity) {
            char[] remap = new char[capacity << 1];
            System.arraycopy(data, 0, remap, 0, capacity);
            data = remap;
            capacity <<= 1;
        }
        data[length - 1] = c;
        width = fm.charsWidth(data, 0, length);
    }

    public void setText(String str) {
        if (str.isEmpty()) return;
        length = str.length();
        data = str.toCharArray();
        capacity = length;
        width = fm.stringWidth(str);
    }

    public void delete() {
        length--;
        if (length < 0) length = 0;
        width = fm.charsWidth(data, 0, length);
    }

    public String getText() {
        if (length == 0) return "";
        return String.valueOf(data, 0, length);
    }
    //endregion COMMUNICATION----->
}
