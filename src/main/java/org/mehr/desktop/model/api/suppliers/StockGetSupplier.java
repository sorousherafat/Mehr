package org.mehr.desktop.model.api.suppliers;

import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.mehr.desktop.model.api.states.StockFirstPageState;
import org.mehr.desktop.model.entities.OnlineStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.Supplier;

public class StockGetSupplier implements Supplier<Map<String, OnlineStock>> {
    private static final int pageSize = 100;
    private static final Logger logger = LoggerFactory.getLogger(StockGetSupplier.class);

    private final HttpHost host;
    private final AsyncClientEndpoint endpoint;
    private final Supplier<String> tokenSupplier;


    public StockGetSupplier(HttpHost host, AsyncClientEndpoint endpoint, Supplier<String> tokenSupplier) {
        this.host = host;
        this.endpoint = endpoint;
        this.tokenSupplier = tokenSupplier;
    }

    @Override
    public Map<String, OnlineStock> get() {
        StockFirstPageSupplier firstPageSupplier = new StockFirstPageSupplier(host, endpoint, tokenSupplier, pageSize);
        StockFirstPageState firstPageState = firstPageSupplier.get();
        Map<String, OnlineStock> firstPageStocks = firstPageState.getStocks();
        logger.info("Retrieved {} stocks in the first page.", firstPageStocks.size());
        int remainingPages = firstPageState.getRemainingPages(pageSize);
        logger.info("{} pages to go.", remainingPages);
        StockRemainingPagesSupplier remainingPagesSupplier = new StockRemainingPagesSupplier(host, endpoint, tokenSupplier, remainingPages, pageSize, firstPageStocks);
        Map<String, OnlineStock> result = remainingPagesSupplier.get();
        logger.info("Retrieved {} stocks.", result.size());
        return result;
    }
}
