package org.mehr.desktop.view.stock;

import org.mehr.desktop.view.base.OpenXLSFileChooserActionListener;
import org.mehr.desktop.view.base.XLSFileChooser;

import java.util.function.Consumer;

public class OpenStockFileChooserActionListener extends OpenXLSFileChooserActionListener {
    public OpenStockFileChooserActionListener(Consumer<XLSFileChooser> consumer) {
        super(new StockFileChooser(), consumer);
    }
}
