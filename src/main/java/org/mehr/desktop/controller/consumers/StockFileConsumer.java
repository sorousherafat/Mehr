package org.mehr.desktop.controller.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.mehr.desktop.model.api.PortalAPI;
import org.mehr.desktop.model.entities.OnSiteStock;
import org.mehr.desktop.model.entities.OnlineStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StockFileConsumer extends XLSFileConsumer<OnSiteStock> {
    private static final Logger logger = LoggerFactory.getLogger(StockFileConsumer.class);

    public StockFileConsumer() {
        reader = row -> {
            String code = row.getStringAt(1);
            String name = row.getStringAt(3);
            int stock = row.getIntAt(16);

            return new OnSiteStock(code, name, stock);
        };

        consumer = records -> {
            try {
                consume(records);
            } catch (ExecutionException | InterruptedException | JsonProcessingException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private void consume(List<OnSiteStock> records) throws ExecutionException, InterruptedException, JsonProcessingException, URISyntaxException {
        List<OnlineStock> stocks;
        try (PortalAPI api = new PortalAPI()) {
            stocks = api.getStocks();
        }
        logger.info("Retrieved {} stocks.", stocks.size());
    }
}
