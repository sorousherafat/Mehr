package org.mehr.desktop.view.id;

import org.mehr.desktop.view.base.OpenXLSFileChooserActionListener;
import org.mehr.desktop.view.base.XLSFileChooser;

import java.util.function.Consumer;

public class OpenIDFileChooserActionListener extends OpenXLSFileChooserActionListener {
    public OpenIDFileChooserActionListener(Consumer<XLSFileChooser> consumer) {
        super(new IDFileChooser(), consumer);
    }
}
