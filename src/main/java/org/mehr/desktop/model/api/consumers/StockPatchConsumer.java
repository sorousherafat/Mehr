package org.mehr.desktop.model.api.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.mehr.desktop.model.api.Path;
import org.mehr.desktop.model.api.inputs.StockPatchInput;
import org.mehr.desktop.model.api.responses.StockPatchResponse;
import org.mehr.desktop.model.entities.OnlineStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Supplier;

public class StockPatchConsumer extends APIConsumer<List<OnlineStock>> {
    private static final Logger logger = LoggerFactory.getLogger(StockPatchConsumer.class);

    public StockPatchConsumer(HttpHost host, AsyncClientEndpoint endpoint, Supplier<String> tokenSupplier, int stockCount) {
        super(host, endpoint, tokenSupplier, Path.STOCKS, stockCount);
        this.success = true;
    }

    private String getBody(OnlineStock stock) throws JsonProcessingException {
        StockPatchInput input = new StockPatchInput(stock.getStock());
        return objectMapper.writeValueAsString(input);
    }

    @Override
    protected void doAction(List<OnlineStock> stocks) throws Exception {
        SimpleRequestBuilder builder = getRequestBuilderWithAuth(SimpleRequestBuilder::patch);
        for (OnlineStock stock : stocks) {
            SimpleHttpRequest request = builder.setPath(Path.STOCKS.with(stock.getId()).toString()).setBody(getBody(stock), ContentType.APPLICATION_JSON).build();
            execute(request);
        }
    }

    @Override
    protected void doComplete(SimpleHttpResponse httpResponse) throws Exception {
        StockPatchResponse response = objectMapper.readValue(httpResponse.getBodyText(), StockPatchResponse.class);
        if (!response.isSuccess()) {
            throw new RuntimeException(response.getDescription());
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
