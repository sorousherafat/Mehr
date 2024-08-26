package org.mehr.desktop.view.filechooser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public abstract class FileChooser extends JFileChooser {
    public FileChooser(String dialogTitle) {
        super(FileSystemView.getFileSystemView());
        FileFilter filter = new FileNameExtensionFilter("XLS Excel files", "xls");
        this.setFileFilter(filter);
        this.setDialogTitle(dialogTitle);
    }

    public int showOpenDialog() {
        return super.showOpenDialog(null);
    }
}
