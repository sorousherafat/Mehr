package org.mehr.desktop.model.id;

import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class IDRecordsConsumer implements Consumer<List<IDRecord>> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void accept(List<IDRecord> IDRecords) {
        IDRecords.forEach(record -> logger.info(record.toString()));
    }
}
