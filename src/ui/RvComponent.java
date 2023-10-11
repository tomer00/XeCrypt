package ui;

import java.awt.Graphics2D;

public class RvComponent {

    private final OnRVClickLis lis;

    public RvComponent(OnRVClickLis lis) {
        this.lis = lis;
    }


    //region COMMUNICATION

    public void draw(Graphics2D g){

    }

    public void onMouseMotion(int x, int y) {

    }

    public void onMouseClick(int x, int y) {

    }

    public void onMouseWheel(boolean isUp) {

    }

    public void updateRv() {

    }

    public interface OnRVClickLis {
        void onclick(int pos,int type);
    }
    //endregion COMMUNICATION
}
