package org.mehr.desktop.model.xls;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileReader<T> {
    private final RecordReader<T> recordReader;

    public FileReader(RecordReader<T> recordReader) {
        this.recordReader = recordReader;
    }

    public List<T> readAll(File file) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            return IntStream.range(1, sheet.getPhysicalNumberOfRows()).parallel().mapToObj(sheet::getRow).map(XLSRow::new).map(recordReader::read).collect(Collectors.toList());
        }
    }
}
