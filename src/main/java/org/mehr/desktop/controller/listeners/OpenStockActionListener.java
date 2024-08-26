package org.mehr.desktop.controller.listeners;

import org.mehr.desktop.controller.consumers.StockFileConsumer;
import org.mehr.desktop.view.filechooser.StockFileChooser;

public class OpenStockActionListener extends OpenFileActionListener {
    public OpenStockActionListener() {
        super(new StockFileChooser(), new StockFileConsumer());
    }
}
