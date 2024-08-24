package org.mehr.desktop.view.base;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public abstract class XLSFileChooser extends JFileChooser {
    public XLSFileChooser(String dialogTitle) {
        super(FileSystemView.getFileSystemView());
        FileFilter filter = new FileNameExtensionFilter("XLS Excel files", "xls");
        this.setFileFilter(filter);
        this.setDialogTitle(dialogTitle);
    }

    public int showOpenDialog() {
        return super.showOpenDialog(null);
    }
}
