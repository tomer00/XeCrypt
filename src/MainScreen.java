import repo.Repo;
import ui.MainView;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

public class MainScreen extends JFrame {

    private JPanel root;
    private final Repo repo = new Repo();
    private volatile boolean isEncrypting = false;

    public MainScreen() {
        this.add(root);
        root.setOpaque(false);
        root.setLayout(new GridLayout(1, 1));
        root.add(new MainView());

        repo.decFile(new File("/home/tom/test/xcrypt/images/x9Hkjtxh1kHkX7NmapZUXaFMcoLUrWxR"));

        DropTarget dropTarget = new DropTarget() {
            public synchronized void drop(DropTargetDropEvent ewt) {
                try {
                    ewt.acceptDrop(DnDConstants.ACTION_REFERENCE);
                    encFiles((List<File>) ewt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                } catch (Exception ignored) {
                }
            }
        };

        this.setDropTarget(dropTarget);
    }


    private void encFiles(List<File> drFiles) {
        new Thread(() -> {
            isEncrypting =true;
            for (var f : drFiles) {
                repo.saveFile(f);
                System.out.println("Encrypting "+f.getAbsoluteFile());
            }
            isEncrypting =false;
        }).start();
    }
}
