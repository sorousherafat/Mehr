package org.mehr.desktop.model.xls;

import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RowRecord;

public class XLSListener implements HSSFListener {
    public static final short sid = RowRecord.sid;
    private boolean hasSeenHeaderRow = false;
    private int rowCount = 0;

    @Override
    public void processRecord(Record record) {
        if (!hasSeenHeaderRow) {
            hasSeenHeaderRow = true;
            return;
        }

        RowRecord rowRecord = (RowRecord) record;
        System.out.printf("start at %d\tend at %d\n", rowRecord.getFirstCol(), rowRecord.getLastCol());
        rowCount += 1;
    }

    public int getRowCount() {
        return rowCount;
    }
}
