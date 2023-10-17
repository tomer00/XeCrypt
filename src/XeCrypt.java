import repo.Repo;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class XeCrypt {
    public static void main(String[] args) {

        //checking if it is a Jar File
        if (!XeCrypt.class.getProtectionDomain().getCodeSource().getLocation().getPath().endsWith("jar")) {
            Repo.ASSETS = XeCrypt.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            Repo.ASSETS += "xcrypt/";


            Repo.PATH_X = XeCrypt.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            Repo.PATH_X += "xcry/";
        }

        LockScreen first = new LockScreen(Repo.getHash().isEmpty());
        first.setSize(220, 400);
        first.setTitle("xCrypt by Himu...");
        first.setIconImage(new ImageIcon(Repo.ASSETS + "xcryLogo.png").getImage());
        first.setVisible(true);
        first.setResizable(false);
        first.setLocationRelativeTo(null);
        first.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}