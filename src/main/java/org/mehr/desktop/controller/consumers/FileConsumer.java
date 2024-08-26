package org.mehr.desktop.controller.consumers;

import org.mehr.desktop.model.xls.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public abstract class FileConsumer<T> implements Consumer<File> {
    protected FileReader<T> reader;
    protected Consumer<List<T>> consumer;

    @Override
    public void accept(File file) {
        try {
            List<T> list = reader.readAll(file);
            consumer.accept(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
