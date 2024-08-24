package org.mehr.desktop.view;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class FileChooser extends JFileChooser {
    public FileChooser() {
        super(FileSystemView.getFileSystemView());
        FileFilter filter = new FileNameExtensionFilter("XLS Excel files", "xls");
        this.setFileFilter(filter);
        this.setDialogTitle("Choose the exported .xls file");
    }

    public int showOpenDialog() {
        return super.showOpenDialog(null);
    }
}
