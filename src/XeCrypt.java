import repo.Repo;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class XeCrypt {
    public static void main(String[] args) {
        LockScreen first = new LockScreen(isFirst());
        first.setSize(220, 400);
        first.setTitle("xCrypt by Himu...");
        first.setIconImage(new ImageIcon(Repo.PATH + "xcryLogo.png").getImage());
        first.setVisible(true);
        first.setResizable(false);
        first.setLocationRelativeTo(null);
        first.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static boolean isFirst() {
        return Repo.getHash().isEmpty();
    }
}