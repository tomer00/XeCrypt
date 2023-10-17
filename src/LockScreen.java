import repo.Repo;
import ui.LockView;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.geom.RoundRectangle2D;

public class LockScreen extends JFrame {
    private JPanel root;

    public LockScreen(boolean isF) {
        this.add(root);

        this.setUndecorated(true);
        this.setShape(new RoundRectangle2D.Float(0, 0, 220, 400, 40, 40));
        root.setOpaque(false);
        root.setLayout(new GridLayout(1, 1));
        root.add(new LockView(isF, () -> {
            this.dispose();

            var first = new MainScreen();
            first.setSize(660, 400);
            first.setTitle("xCrypt by Tomer...");
            first.setIconImage(new ImageIcon(Repo.ASSETS + "xcryLogo.png").getImage());
            first.setVisible(true);
            first.setLocationRelativeTo(null);
            first.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }));
    }
}
