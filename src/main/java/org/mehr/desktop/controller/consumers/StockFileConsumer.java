package org.mehr.desktop.controller.consumers;

import org.mehr.desktop.model.entities.Stock;
import org.mehr.desktop.model.xls.FileReader;

import java.util.logging.Logger;

public class StockFileConsumer extends FileConsumer<Stock> {
    private final static Logger logger = Logger.getLogger(StockFileConsumer.class.getName());

    public StockFileConsumer() {
        reader = new FileReader<>(row -> {
            String code = row.getStringAt(1);
            String name = row.getStringAt(3);
            int stock = row.getIntAt(16);

            return new Stock(code, name, stock);
        });

        consumer = records -> {
            for (Stock record : records) {
                logger.info(record.toString());
            }
        };
    }
}
