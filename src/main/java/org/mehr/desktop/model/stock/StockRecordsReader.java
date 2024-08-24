package org.mehr.desktop.model.stock;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.mehr.desktop.model.xls.XLSFileReader;

public class StockRecordsReader extends XLSFileReader<StockRecord> {
    StockRecordsReader(DataFormatter formatter) {
        super(row -> new StockRecord(formatter.formatCellValue(row.getCell(1)), formatter.formatCellValue(row.getCell(3)), Integer.parseInt(formatter.formatCellValue(row.getCell(16)))));
    }
}
