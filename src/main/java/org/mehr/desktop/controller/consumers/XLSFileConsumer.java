package org.mehr.desktop.controller.consumers;

import org.mehr.desktop.model.xls.FileReader;
import org.mehr.desktop.model.xls.RecordReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public abstract class XLSFileConsumer<T> implements Consumer<File> {
    protected RecordReader<T> reader;
    protected Consumer<List<T>> consumer;

    @Override
    public void accept(File file) {
        FileReader<T> fileReader = new FileReader<>(reader);
        try {
            List<T> list = fileReader.readAll(file);
            consumer.accept(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
