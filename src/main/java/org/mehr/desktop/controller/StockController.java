package org.mehr.desktop.controller;

import org.mehr.desktop.controller.listeners.OpenFileActionListener;
import org.mehr.desktop.controller.listeners.OpenStockActionListener;
import org.mehr.desktop.view.buttons.StockButton;

public class StockController {
    private final StockButton button;

    private final OpenFileActionListener openFileActionListener;

    public StockController(StockButton button) {
        this.button = button;

        this.openFileActionListener = new OpenStockActionListener();

        addActionListeners();
    }

    private void addActionListeners() {
        button.addActionListener(openFileActionListener);
    }
}
