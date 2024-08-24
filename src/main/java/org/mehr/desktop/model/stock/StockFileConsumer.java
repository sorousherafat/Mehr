package org.mehr.desktop.model.stock;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.mehr.desktop.model.xls.XLSFileConsumer;

public class StockFileConsumer extends XLSFileConsumer<StockRecord> {
    public StockFileConsumer() {
        reader = new StockRecordsReader(new DataFormatter());
        consumer = new StockRecordsConsumer();
    }
}
