package org.mehr.desktop.model.xls;

public interface RecordReader<T> {

    T read(XLSRow row);
}
