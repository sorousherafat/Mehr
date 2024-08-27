package org.mehr.desktop.model.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.async.methods.*;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;

import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

public abstract class APIBase implements FutureCallback<SimpleHttpResponse> {
    protected static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    protected final HttpHost host;
    protected final AsyncClientEndpoint endpoint;
    protected final Path path;
    protected final CountDownLatch latch;
    protected final Supplier<String> tokenSupplier;

    protected boolean success = false;

    protected APIBase(HttpHost host, AsyncClientEndpoint endpoint, Supplier<String> tokenSupplier, Path path, int countDown) {
        this.host = host;
        this.endpoint = endpoint;
        this.tokenSupplier = tokenSupplier;
        this.path = path;
        this.latch = new CountDownLatch(countDown);
    }

    protected abstract void doComplete(SimpleHttpResponse httpResponse) throws Exception;

    protected abstract void doFail(Exception e) throws Exception;

    protected abstract void doCancel() throws Exception;

    protected SimpleRequestBuilder getRequestBuilder(Supplier<SimpleRequestBuilder> method) {
        return method.get().setHttpHost(host).setPath(path.toString());
    }

    protected SimpleRequestBuilder getRequestBuilderWithAuth(Supplier<SimpleRequestBuilder> method) {
        return getRequestBuilder(method).addHeader("Authorization", String.format("Bearer %s", tokenSupplier.get()));
    }

    protected void execute(SimpleHttpRequest request) {
        endpoint.execute(SimpleRequestProducer.create(request), SimpleResponseConsumer.create(), this);
    }

    @Override
    public void completed(SimpleHttpResponse response) {
        try {
            doComplete(response);
            latch.countDown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void failed(Exception e) {
        try {
            doFail(e);
            latch.countDown();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void cancelled() {
        try {
            doCancel();
            latch.countDown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
