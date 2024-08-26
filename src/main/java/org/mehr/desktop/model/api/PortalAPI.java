package org.mehr.desktop.model.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.async.methods.*;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.async.MinimalHttpAsyncClient;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.config.Http1Config;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.http2.config.H2Config;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.mehr.desktop.model.api.inputs.AuthenticationInput;
import org.mehr.desktop.model.api.responses.AuthenticationResponse;
import org.mehr.desktop.model.api.responses.StockResponse;
import org.mehr.desktop.model.api.responses.StocksResponse;
import org.mehr.desktop.model.entities.OnlineStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import static org.mehr.desktop.App.properties;

public class PortalAPI implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(PortalAPI.class);
    private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final int pageSize = 100;

    private final HttpHost host;
    MinimalHttpAsyncClient client;
    private final AsyncClientEndpoint endpoint;

    private String token;

    public PortalAPI() throws ExecutionException, InterruptedException {
        host = new HttpHost("https", properties.getProperty("host"));
        client = HttpAsyncClients.createMinimal(H2Config.DEFAULT, Http1Config.DEFAULT, IOReactorConfig.DEFAULT, PoolingAsyncClientConnectionManagerBuilder.create().setDefaultTlsConfig(TlsConfig.custom().setVersionPolicy(HttpVersionPolicy.FORCE_HTTP_2).build()).build());
        client.start();
        endpoint = client.lease(host, null).get();
    }

    public List<OnlineStock> getStocks() throws URISyntaxException, InterruptedException, JsonProcessingException {
        List<OnlineStock> stocks = new ArrayList<>();
        StocksResponse responseState = new StocksResponse();
        SimpleHttpRequest request = SimpleRequestBuilder.get().setHttpHost(host).setPath(Path.GET_STOCKS.toString()).addHeader("Authorization", String.format("Bearer %s", getToken())).build();
        URI uri = new URIBuilder(request.getUri()).addParameter("size", String.valueOf(pageSize)).build();
        request.setUri(uri);
        CountDownLatch latch = new CountDownLatch(1);
        SimpleHttpRequest finalRequest = request;
        endpoint.execute(SimpleRequestProducer.create(request), SimpleResponseConsumer.create(), new FutureCallback<SimpleHttpResponse>() {
            @Override
            public void completed(SimpleHttpResponse simpleHttpResponse) {
                String json = simpleHttpResponse.getBodyText();
                try {
                    StocksResponse response = objectMapper.readValue(json, StocksResponse.class);
                    responseState.setSuccess(true);
                    responseState.setTotal(response.getTotal());
                    responseState.setCount(response.getCount());
                    for (StockResponse stock : response.getVariants()) {
                        stocks.add(stock.toOnlineStock());
                    }
                    latch.countDown();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void failed(Exception e) {
                logger.error("Request failed.", e);
                responseState.setSuccess(false);
                latch.countDown();
            }

            @Override
            public void cancelled() {
                logger.error("Request at {} cancelled.", finalRequest.getPath());
                responseState.setSuccess(false);
                latch.countDown();
            }
        });

        latch.await();

        if (!responseState.isSuccess()) {
            throw new RuntimeException("Unsuccessful API call");
        }

        int remainingPages = (int) Math.ceil((float) (responseState.getTotal() - responseState.getCount()) / pageSize);
        logger.info("{} remaining pages.", remainingPages);

        CountDownLatch pagesLatch = new CountDownLatch(remainingPages);

        for (int currentPage = 0; currentPage < remainingPages; currentPage++) {
            request = SimpleRequestBuilder.get().setHttpHost(host).setPath(Path.GET_STOCKS.toString()).addHeader("Authorization", String.format("Bearer %s", getToken())).build();
            uri = new URIBuilder(request.getUri()).addParameter("size", String.valueOf(pageSize)).addParameter("page", String.valueOf(2 + currentPage)).build();
            request.setUri(uri);
            int finalCurrentPage = currentPage;
            logger.info("Executing request for page #{}.", currentPage);
            endpoint.execute(SimpleRequestProducer.create(request), SimpleResponseConsumer.create(), new FutureCallback<SimpleHttpResponse>() {
                @Override
                public void completed(SimpleHttpResponse simpleHttpResponse) {
                    String json = simpleHttpResponse.getBodyText();
                    try {
                        StocksResponse response = objectMapper.readValue(json, StocksResponse.class);
                        for (StockResponse stock : response.getVariants()) {
                            stocks.add(stock.toOnlineStock());
                        }
                        pagesLatch.countDown();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void failed(Exception e) {
                    logger.error("Request failed.", e);
                    responseState.setSuccess(false);
                    pagesLatch.countDown();
                }

                @Override
                public void cancelled() {
                    logger.error("Request at {} cancelled.", finalCurrentPage);
                    responseState.setSuccess(false);
                    pagesLatch.countDown();
                }
            });
        }

        pagesLatch.await();

        if (!responseState.isSuccess()) {
            throw new RuntimeException("Unsuccessful API call");
        }

        logger.info("Count of stocks: {}.", stocks.size());

        return stocks;
    }

    private String getToken() throws InterruptedException, JsonProcessingException {
        if (token == null) {
            authenticate();
        }

        return token;
    }

    private void authenticate() throws InterruptedException, JsonProcessingException {
        AuthenticationInput input = new AuthenticationInput(properties.getProperty("username"), properties.getProperty("password"));
        String body = objectMapper.writeValueAsString(input);
        SimpleHttpRequest request = SimpleRequestBuilder.post().setHttpHost(host).setPath(Path.AUTHENTICATE.toString()).setBody(body, ContentType.APPLICATION_JSON).build();
        CountDownLatch latch = new CountDownLatch(1);
        logger.info("Executing endpoint.");
        endpoint.execute(SimpleRequestProducer.create(request), SimpleResponseConsumer.create(), new FutureCallback<SimpleHttpResponse>() {
            @Override
            public void completed(SimpleHttpResponse simpleHttpResponse) {
                logger.info("Request completed.");
                String json = simpleHttpResponse.getBodyText();
                logger.info("Response body: {}.", json);
                try {
                    AuthenticationResponse response = objectMapper.readValue(json, AuthenticationResponse.class);
                    logger.info(response.toString());
                    if (!response.isSuccess()) {
                        logger.error(response.getDescription());
                        throw new RuntimeException(response.getDescription());
                    }
                    token = response.getToken();
                    latch.countDown();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void failed(Exception e) {
                logger.error("Request failed.", e);
                latch.countDown();
            }

            @Override
            public void cancelled() {
                logger.error("Request at {} cancelled.", request.getPath());
                latch.countDown();
            }
        });

        latch.await();

        if (token == null) {
            throw new RuntimeException("Null token");
        }

        logger.info("Got token {}.", token);
    }

    @Override
    public void close() {
        client.close(CloseMode.GRACEFUL);
    }
}
