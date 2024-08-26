package org.mehr.desktop.model.xls;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public class XLSRow {
    public static final DataFormatter formatter = new DataFormatter();

    private final Row row;

    public XLSRow(Row row) {
        this.row = row;
    }

    public String getStringAt(int index) {
        return formatter.formatCellValue(row.getCell(index));
    }

    public int getIntAt(int index) {
        return Integer.parseInt(getStringAt(index));
    }
}
