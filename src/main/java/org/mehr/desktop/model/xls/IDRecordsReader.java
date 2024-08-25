package org.mehr.desktop.model.xls;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.mehr.desktop.model.entities.ID;

public class IDRecordsReader extends XLSFileReader<ID> {
    public IDRecordsReader(DataFormatter formatter) {
        super(row -> new ID(formatter.formatCellValue(row.getCell(0)), formatter.formatCellValue(row.getCell(1))));
    }
}
