package org.mehr.desktop.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class OpenFileChooserActionListener implements ActionListener {
    private final Consumer<FileChooser> consumer;

    public OpenFileChooserActionListener(Consumer<FileChooser> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        int result = fileChooser.showOpenDialog();
        if (result == FileChooser.APPROVE_OPTION) {
            Thread thread = new Thread(() -> consumer.accept(fileChooser), "Worker");
            thread.start();
        }
    }
}
