package org.mehr.desktop.model.consumers;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.mehr.desktop.model.entities.ID;
import org.mehr.desktop.model.repositories.IDRepository;
import org.mehr.desktop.model.xls.IDRecordsReader;
import org.mehr.desktop.model.xls.XLSFileConsumer;

import java.sql.SQLException;

public class IDFileConsumer extends XLSFileConsumer<ID> {
    public IDFileConsumer() {
        reader = new IDRecordsReader(new DataFormatter());
        consumer = records -> {
            try {
                IDRepository database = new IDRepository();
                database.replaceIDs(records);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
