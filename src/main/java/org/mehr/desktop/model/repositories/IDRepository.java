package org.mehr.desktop.model.repositories;

import org.mehr.desktop.model.entities.ID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IDRepository extends Repository {
    public IDRepository() throws SQLException {
    }

    @Override
    String createTableQuery() {
        return "CREATE TABLE IF NOT EXISTS ids (code VARCHAR(256) UNIQUE, portal VARCHAR(256) UNIQUE)";
    }

    public List<ID> readIDs() throws SQLException {
        List<ID> records = new ArrayList<>();

        String query = "SELECT (code, portal) FROM ids";

        try (Connection connection = createConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                String portal = resultSet.getString("portal");
                records.add(new ID(code, portal));
            }
        }

        return records;
    }

    public void replaceIDs(List<ID> records) throws SQLException {
        try (Connection connection = createConnection()) {
            connection.setAutoCommit(false);

            deleteIDs(connection);
            insertIDs(connection, records);

            connection.commit();
        }
    }

    private void deleteIDs(Connection connection) throws SQLException {
        String query = "DELETE FROM ids";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    private void insertIDs(Connection connection, List<ID> records) throws SQLException {
        String query = "INSERT INTO ids (code, portal) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (ID record : records) {
                statement.setString(1, record.getCode());
                statement.setString(2, record.getPortal());
                statement.addBatch();
            }

            statement.executeBatch();
        }
    }
}