package org.mehr.desktop.controller;

import org.mehr.desktop.model.consumers.StockFileConsumer;
import org.mehr.desktop.view.base.OpenXLSFileChooserActionListener;
import org.mehr.desktop.view.stock.OpenStockFileChooserActionListener;
import org.mehr.desktop.view.stock.StockButton;

public class StockController {
    private final StockButton stockButton;

    private final OpenXLSFileChooserActionListener openFileChooserActionListener;

    public StockController(StockButton stockButton) {
        this.stockButton = stockButton;

        this.openFileChooserActionListener = new OpenStockFileChooserActionListener((fileChooser) -> new StockFileConsumer().accept(fileChooser.getSelectedFile()));

        addActionListeners();
    }

    private void addActionListeners() {
        stockButton.addActionListener(openFileChooserActionListener);
    }
}
