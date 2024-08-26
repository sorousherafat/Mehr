package org.mehr.desktop.controller.listeners;

import org.mehr.desktop.view.filechooser.FileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;

public abstract class OpenFileActionListener implements ActionListener {
    private final FileChooser fileChooser;
    private final Consumer<File> consumer;

    public OpenFileActionListener(FileChooser fileChooser, Consumer<File> consumer) {
        this.fileChooser = fileChooser;
        this.consumer = consumer;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int result = fileChooser.showOpenDialog();
        if (result == FileChooser.APPROVE_OPTION) {
            Thread thread = new Thread(() -> consumer.accept(fileChooser.getSelectedFile()));
            thread.start();
        }
    }
}
