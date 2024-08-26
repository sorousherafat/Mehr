package org.mehr.desktop;

import org.mehr.desktop.view.MainFrame;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class App {
    public static final Properties properties = new Properties();

    public static void main(String[] args) throws IOException {
        loadProperties();
        SwingUtilities.invokeLater(MainFrame::new);
    }

    private static void loadProperties() throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "application.properties";
        properties.load(Files.newInputStream(Paths.get(path)));
    }
}
