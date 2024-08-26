package org.mehr.desktop.controller.listeners;

import org.mehr.desktop.controller.consumers.IDFileConsumer;
import org.mehr.desktop.view.filechooser.IDFileChooser;

public class OpenIDActionListener extends OpenFileActionListener {
    public OpenIDActionListener() {
        super(new IDFileChooser(), new IDFileConsumer());
    }
}
