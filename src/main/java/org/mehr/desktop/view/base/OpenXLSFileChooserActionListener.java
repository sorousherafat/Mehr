package org.mehr.desktop.view.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public abstract class OpenXLSFileChooserActionListener implements ActionListener {
    private final XLSFileChooser fileChooser;
    private final Consumer<XLSFileChooser> consumer;

    public OpenXLSFileChooserActionListener(XLSFileChooser fileChooser, Consumer<XLSFileChooser> consumer) {
        this.fileChooser = fileChooser;
        this.consumer = consumer;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int result = fileChooser.showOpenDialog();
        if (result == XLSFileChooser.APPROVE_OPTION) {
            Thread thread = new Thread(() -> consumer.accept(fileChooser), "Worker");
            thread.start();
        }
    }
}
