package ui;

import repo.Repo;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.io.File;

public class ActiImg extends JFrame {
    private JPanel root = new JPanel();

    ActiImg(File f) {

        this.setIconImage(new ImageIcon(Repo.PATH + "image.png").getImage());
        this.setTitle(f.getName());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setSize(600, 400);

        this.add(root);
        this.setLayout(new GridLayout(1, 1));
    }
}