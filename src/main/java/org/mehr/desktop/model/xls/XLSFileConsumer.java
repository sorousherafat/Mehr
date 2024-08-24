package org.mehr.desktop.model.xls;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

public abstract class XLSFileConsumer<T> implements Consumer<File> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    protected XLSFileReader<T> reader;
    protected Consumer<List<T>> consumer;

    @Override
    public void accept(File file) {
        try {
            List<T> list = reader.read(file);
            consumer.accept(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
