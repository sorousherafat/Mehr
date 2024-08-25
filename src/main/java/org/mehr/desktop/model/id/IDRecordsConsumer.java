package org.mehr.desktop.model.id;

import org.mehr.desktop.model.repositories.IDRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class IDRecordsConsumer implements Consumer<List<IDRecord>> {
    private static final Logger logger = Logger.getLogger(IDRecordsConsumer.class.getName());

    @Override
    public void accept(List<IDRecord> records) {
        try {
            IDRepository database = new IDRepository();
            database.replaceIDs(records);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
