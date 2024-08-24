package org.mehr.desktop.model.id;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.mehr.desktop.model.xls.XLSFileConsumer;

public class IDFileConsumer extends XLSFileConsumer<IDRecord> {
    public IDFileConsumer() {
        reader = new IDRecordsReader(new DataFormatter());
        consumer = new IDRecordsConsumer();
    }
}
