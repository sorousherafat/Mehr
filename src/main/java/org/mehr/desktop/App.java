package org.mehr.desktop;

import org.mehr.desktop.view.MainFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
