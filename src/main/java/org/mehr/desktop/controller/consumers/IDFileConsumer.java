package org.mehr.desktop.controller.consumers;

import org.mehr.desktop.model.entities.ID;
import org.mehr.desktop.model.repositories.IDRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class IDFileConsumer extends XLSFileConsumer<ID> {
    private static final Logger logger = LoggerFactory.getLogger(IDFileConsumer.class);

    public IDFileConsumer() {
        reader = row -> {
            String code = row.getStringAt(0);
            String portal = row.getStringAt(1);
            return new ID(code, portal);
        };

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
