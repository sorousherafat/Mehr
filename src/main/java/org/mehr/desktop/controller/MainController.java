package org.mehr.desktop.controller;

import org.mehr.desktop.model.xls.XLSFileConsumer;
import org.mehr.desktop.view.OpenFileChooserActionListener;
import org.mehr.desktop.view.OpenButton;
import org.mehr.desktop.view.ProgressBar;

import java.util.logging.Logger;

public class MainController {
    private final OpenButton openButton;
    private final ProgressBar progressBar;

    private final OpenFileChooserActionListener openFileChooserActionListener;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public MainController(OpenButton openButton, ProgressBar progressBar) {
        this.openButton = openButton;
        this.progressBar = progressBar;

        this.openFileChooserActionListener = new OpenFileChooserActionListener((fileChooser) -> new XLSFileConsumer().accept(fileChooser.getSelectedFile()));

        addActionListeners();
    }

    private void addActionListeners() {
        openButton.addActionListener(openFileChooserActionListener);
    }
}
