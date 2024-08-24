package org.mehr.desktop.controller;

import org.mehr.desktop.model.id.IDFileConsumer;
import org.mehr.desktop.view.base.OpenXLSFileChooserActionListener;
import org.mehr.desktop.view.id.IDButton;
import org.mehr.desktop.view.id.OpenIDFileChooserActionListener;

public class IDController {
    private final IDButton idButton;

    private final OpenXLSFileChooserActionListener openFileChooserActionListener;

    public IDController(IDButton idButton) {
        this.idButton = idButton;

        this.openFileChooserActionListener = new OpenIDFileChooserActionListener((fileChooser) -> new IDFileConsumer().accept(fileChooser.getSelectedFile()));

        addActionListeners();
    }

    private void addActionListeners() {
        idButton.addActionListener(openFileChooserActionListener);
    }
}
