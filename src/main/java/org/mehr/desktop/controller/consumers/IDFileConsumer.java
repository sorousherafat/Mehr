package org.mehr.desktop.controller.consumers;

import org.mehr.desktop.model.entities.ID;
import org.mehr.desktop.model.repositories.IDRepository;
import org.mehr.desktop.model.xls.FileReader;

import java.sql.SQLException;
import java.util.logging.Logger;

public class IDFileConsumer extends FileConsumer<ID> {
    private final static Logger logger = Logger.getLogger(IDFileConsumer.class.getName());

    public IDFileConsumer() {
        reader = new FileReader<>(row -> {
            String code = row.getStringAt(0);
            String portal = row.getStringAt(1);
            return new ID(code, portal);
        });

        consumer = records -> {
            for (ID record : records) {
                logger.info(record.toString());
            }

            try {
                IDRepository database = new IDRepository();
                database.replaceIDs(records);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
