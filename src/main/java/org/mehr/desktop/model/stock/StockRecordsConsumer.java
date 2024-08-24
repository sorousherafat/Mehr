package org.mehr.desktop.model.stock;

import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class StockRecordsConsumer implements Consumer<List<StockRecord>> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void accept(List<StockRecord> stockRecords) {
        stockRecords.forEach(record -> logger.info(record.toString()));
    }
}
