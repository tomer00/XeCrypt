import ui.LockView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class LockScreen extends JFrame {
    private JPanel root;

    public LockScreen() {
        this.add(root);

        root.setLayout(new GridLayout(1, 1));
        root.add(new LockView());
    }
}
