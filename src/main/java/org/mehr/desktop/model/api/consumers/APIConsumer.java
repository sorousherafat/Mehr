package org.mehr.desktop.model.api.consumers;

import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.mehr.desktop.model.api.APIBase;
import org.mehr.desktop.model.api.Path;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class APIConsumer<T> extends APIBase implements Consumer<T> {
    protected APIConsumer(HttpHost host, AsyncClientEndpoint endpoint, Supplier<String> tokenSupplier, Path path, int countDown) {
        super(host, endpoint, tokenSupplier, path, countDown);
    }

    protected abstract void doAction(T t) throws Exception;

    @Override
    public void accept(T t) {
        try {
            doAction(t);
            latch.await();

            if (!success) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
