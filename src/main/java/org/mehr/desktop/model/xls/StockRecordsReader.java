package org.mehr.desktop.model.xls;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.mehr.desktop.model.entities.Stock;

public class StockRecordsReader extends XLSFileReader<Stock> {
    public StockRecordsReader(DataFormatter formatter) {
        super(row -> new Stock(formatter.formatCellValue(row.getCell(1)), formatter.formatCellValue(row.getCell(3)), Integer.parseInt(formatter.formatCellValue(row.getCell(16)))));
    }
}
