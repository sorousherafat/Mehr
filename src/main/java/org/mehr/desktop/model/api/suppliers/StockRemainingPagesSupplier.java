package org.mehr.desktop.model.api.suppliers;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.apache.hc.core5.net.URIBuilder;
import org.mehr.desktop.model.api.Path;
import org.mehr.desktop.model.api.responses.StockPageResponse;
import org.mehr.desktop.model.entities.OnlineStock;
import org.mehr.desktop.model.mappers.OnlineStockMapMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;
import java.util.function.Supplier;

public class StockRemainingPagesSupplier extends APISupplier<Map<String, OnlineStock>> {
    private static final Logger logger = LoggerFactory.getLogger(StockRemainingPagesSupplier.class);

    private final int pageCount;
    private final int pageSize;

    public StockRemainingPagesSupplier(HttpHost host, AsyncClientEndpoint endpoint, Supplier<String> tokenSupplier, int pageCount, int pageSize, Map<String, OnlineStock> state) {
        super(host, endpoint, tokenSupplier, Path.STOCKS, pageCount);
        this.pageCount = pageCount;
        this.pageSize = pageSize;
        this.success = true;
        this.state = state;
    }

    @Override
    protected void doAction() throws Exception {
        SimpleRequestBuilder builder = getRequestBuilderWithAuth(SimpleRequestBuilder::get);
        URI baseURI = new URIBuilder(builder.getUri()).addParameter("size", String.valueOf(pageSize)).build();
        for (int pageNumber = 0; pageNumber < pageCount; pageNumber++) {
            URI uri = new URIBuilder(baseURI).addParameter("page", String.valueOf(2 + pageNumber)).build();
            SimpleHttpRequest request = builder.setUri(uri).build();
            execute(request);
        }
    }

    @Override
    protected void doComplete(SimpleHttpResponse httpResponse) throws Exception {
        StockPageResponse response = objectMapper.readValue(httpResponse.getBodyText(), StockPageResponse.class);
        synchronized (this) {
            state.putAll(new OnlineStockMapMapper().map(response));
        }
    }

    @Override
    protected void doFail(Exception e) throws Exception {
        success = false;
        logger.error("Request failed.", e);
    }

    @Override
    protected void doCancel() throws Exception {
        success = false;
        logger.error("Request cancelled.");
    }
}
