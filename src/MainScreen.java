import repo.Repo;
import repo.RepoType;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.File;

public class MainScreen extends JFrame {

    private JPanel root;
    private final Repo repo = new Repo(RepoType.IMAGE);

    public MainScreen() {
        this.add(root);
    }
}
