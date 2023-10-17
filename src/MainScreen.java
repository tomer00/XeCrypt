import repo.Repo;
import ui.MainView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

public class MainScreen extends JFrame {

    private JPanel root;
    private final Repo repo = new Repo();
    private final MainView mainView;

    public MainScreen() {
        this.add(root);
        root.setOpaque(false);
        root.setLayout(new GridLayout(1, 1));
        mainView = new MainView(repo);
        root.add(mainView);

        DropTarget dropTarget = new DropTarget() {
            public synchronized void drop(DropTargetDropEvent ewt) {
                try {
                    ewt.acceptDrop(DnDConstants.ACTION_REFERENCE);
                    encFiles((List<File>) ewt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                    mainView.dropEvent(false);
                } catch (Exception ignored) {
                }
            }

            @Override
            public synchronized void dragEnter(DropTargetDragEvent dtde) {
                super.dragEnter(dtde);
                mainView.dropEvent(true);
            }

            @Override
            public synchronized void dragExit(DropTargetEvent dte) {
                super.dragExit(dte);
                mainView.dropEvent(false);
            }
        };

        this.setDropTarget(dropTarget);
    }


    private void encFiles(List<File> drFiles) {
        mainView.encrypting(drFiles);
        new Thread(() -> {
            for (var f : drFiles)
                repo.saveFile(f);
            mainView.encrypted(drFiles);
        }).start();
    }
}
