package org.mehr.desktop.model.id;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.mehr.desktop.model.xls.XLSFileReader;

public class IDRecordsReader extends XLSFileReader<IDRecord> {
    IDRecordsReader(DataFormatter formatter) {
        super(row -> new IDRecord(formatter.formatCellValue(row.getCell(0)), formatter.formatCellValue(row.getCell(1))));
    }
}
