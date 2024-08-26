package org.mehr.desktop.controller;

import org.mehr.desktop.controller.listeners.OpenFileActionListener;
import org.mehr.desktop.controller.listeners.OpenIDActionListener;
import org.mehr.desktop.view.buttons.IDButton;

public class IDController {
    private final IDButton button;
    private final OpenFileActionListener openFileActionListener;

    public IDController(IDButton button) {
        this.button = button;
        this.openFileActionListener = new OpenIDActionListener();

        addActionListeners();
    }

    private void addActionListeners() {
        button.addActionListener(openFileActionListener);
    }
}
