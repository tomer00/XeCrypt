package ui;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MainView extends JComponent {

    public MainView(){

    }

    @Override
    public void paint(Graphics _g) {
        Graphics2D g = (Graphics2D) _g.create();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());
    }

}
