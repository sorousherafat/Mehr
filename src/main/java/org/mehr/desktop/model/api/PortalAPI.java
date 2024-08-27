package org.mehr.desktop.model.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.async.MinimalHttpAsyncClient;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.config.Http1Config;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.http2.config.H2Config;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.mehr.desktop.model.api.consumers.StockPatchConsumer;
import org.mehr.desktop.model.api.suppliers.AuthenticationSupplier;
import org.mehr.desktop.model.api.suppliers.StockGetSupplier;
import org.mehr.desktop.model.entities.OnlineStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.mehr.desktop.App.properties;

public class PortalAPI implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(PortalAPI.class);
    private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final int pageSize = 100;

    private final HttpHost host;
    private final AsyncClientEndpoint endpoint;
    MinimalHttpAsyncClient client;
    private String token;

    public PortalAPI() throws ExecutionException, InterruptedException {
        this.host = new HttpHost("https", properties.getProperty("host"));
        this.client = HttpAsyncClients.createMinimal(H2Config.DEFAULT, Http1Config.DEFAULT, IOReactorConfig.DEFAULT, PoolingAsyncClientConnectionManagerBuilder.create().setDefaultTlsConfig(TlsConfig.custom().setVersionPolicy(HttpVersionPolicy.FORCE_HTTP_2).build()).build());
        this.client.start();
        this.endpoint = this.client.lease(host, null).get();
    }

    public Map<String, OnlineStock> getStocks() {
        Supplier<Map<String, OnlineStock>> supplier = new StockGetSupplier(host, endpoint, this::getToken);
        return supplier.get();
    }

    public void updateStocks(List<OnlineStock> stocks) {
        Consumer<List<OnlineStock>> consumer = new StockPatchConsumer(host, endpoint, this::getToken, stocks.size());
        consumer.accept(stocks);
    }

    @Override
    public void close() {
        client.close(CloseMode.GRACEFUL);
    }

    private String getToken() {
        if (token == null) {
            Supplier<String> supplier = new AuthenticationSupplier(host, endpoint, this::getToken);
            token = supplier.get();
        }

        return token;
    }
}
