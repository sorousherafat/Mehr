package org.mehr.desktop.view;

import org.mehr.desktop.controller.IDController;
import org.mehr.desktop.controller.StockController;
import org.mehr.desktop.view.buttons.IDButton;
import org.mehr.desktop.view.buttons.StockButton;

import javax.swing.*;

public class MainFrame extends JFrame {
    private final JPanel panel = new JPanel();
    private final StockButton stockButton = new StockButton();
    private final IDButton idButton = new IDButton();

    public MainFrame() {
        super("Mehr");

        new StockController(stockButton);
        new IDController(idButton);

        addComponents();
        config();
        pack();
        setVisible(true);
    }

    private void config() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addComponents() {
        panel.add(stockButton);
        panel.add(idButton);
        add(panel);
    }
}
