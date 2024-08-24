package org.mehr.desktop.view;

import org.mehr.desktop.controller.MainController;

import javax.swing.*;

public class MainFrame extends JFrame {
    private final JPanel panel = new JPanel();
    private final OpenButton openButton = new OpenButton();
    private final ProgressBar progressBar = new ProgressBar();

    public MainFrame() {
        super("Mehr");

        new MainController(openButton, progressBar);

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
        panel.add(openButton);
        panel.add(progressBar);
        add(panel);
    }
}
