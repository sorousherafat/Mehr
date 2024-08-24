package org.mehr.desktop.model.xls;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class XLSFileReader<T> {
    private final Function<Row, T> function;

    protected XLSFileReader(Function<Row, T> function) {
        this.function = function;
    }

    public List<T> read(File file) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            return IntStream.range(1, sheet.getPhysicalNumberOfRows()).parallel().mapToObj(sheet::getRow).map(function).collect(Collectors.toList());
        }
    }
}
