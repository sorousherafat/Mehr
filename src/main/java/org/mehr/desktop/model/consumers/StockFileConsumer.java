package org.mehr.desktop.model.consumers;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.mehr.desktop.model.entities.Stock;
import org.mehr.desktop.model.xls.StockRecordsReader;
import org.mehr.desktop.model.xls.XLSFileConsumer;

import java.util.logging.Logger;

public class StockFileConsumer extends XLSFileConsumer<Stock> {
    private final static Logger logger = Logger.getLogger(StockFileConsumer.class.getName());

    public StockFileConsumer() {
        reader = new StockRecordsReader(new DataFormatter());
        consumer = records -> {
            for (Stock record : records) {
                logger.info(record.toString());
            }
        };
    }
}
