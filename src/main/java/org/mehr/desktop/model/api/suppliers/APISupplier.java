package org.mehr.desktop.model.api.suppliers;

import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.mehr.desktop.model.api.APIBase;
import org.mehr.desktop.model.api.Path;

import java.util.function.Supplier;

public abstract class APISupplier<T> extends APIBase implements Supplier<T> {
    protected T state;

    protected APISupplier(HttpHost host, AsyncClientEndpoint endpoint, Supplier<String> tokenSupplier, Path path, int countDown) {
        super(host, endpoint, tokenSupplier, path, countDown);
    }

    protected abstract void doAction() throws Exception;

    @Override
    public T get() {
        try {
            doAction();
            latch.await();

            if (!success) {
                throw new RuntimeException();
            }

            return state;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
