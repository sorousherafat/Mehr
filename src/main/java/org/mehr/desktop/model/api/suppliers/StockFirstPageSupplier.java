package org.mehr.desktop.model.api.suppliers;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.apache.hc.core5.net.URIBuilder;
import org.mehr.desktop.model.api.Path;
import org.mehr.desktop.model.api.responses.StockPageResponse;
import org.mehr.desktop.model.api.states.StockFirstPageState;
import org.mehr.desktop.model.mappers.StockFirstPageStateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.function.Supplier;

public class StockFirstPageSupplier extends APISupplier<StockFirstPageState> {
    private static final Logger logger = LoggerFactory.getLogger(StockFirstPageSupplier.class);

    private final int pageSize;

    public StockFirstPageSupplier(HttpHost host, AsyncClientEndpoint endpoint, Supplier<String> tokenSupplier, int pageSize) {
        super(host, endpoint, tokenSupplier, Path.STOCKS, 1);
        this.pageSize = pageSize;
    }

    @Override
    protected void doAction() throws Exception {
        SimpleRequestBuilder builder = getRequestBuilderWithAuth(SimpleRequestBuilder::get);
        URI uri = new URIBuilder(builder.getUri()).addParameter("size", String.valueOf(pageSize)).build();
        SimpleHttpRequest request = builder.setUri(uri).build();
        execute(request);
    }

    @Override
    protected void doComplete(SimpleHttpResponse httpResponse) throws Exception {
        StockPageResponse response = objectMapper.readValue(httpResponse.getBodyText(), StockPageResponse.class);
        success = true;
        state = new StockFirstPageStateMapper().map(response);
    }

    @Override
    protected void doFail(Exception e) throws Exception {
        logger.error("Request failed.", e);
    }

    @Override
    protected void doCancel() throws Exception {
        logger.error("Request cancelled.");
    }
}
