package org.mehr.desktop.model.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Repository {
    private static final String url = "jdbc:sqlite:mehr.db";

    public Repository() throws SQLException {
        createTablesIfNotExist();
    }

    abstract String createTableQuery();

    Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    private void createTablesIfNotExist() throws SQLException {
        String query = createTableQuery();

        try (Connection connection = createConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }
}