package org.mehr.desktop.controller.consumers;

import org.mehr.desktop.model.api.PortalAPI;
import org.mehr.desktop.model.entities.OnSiteStock;
import org.mehr.desktop.model.entities.OnlineStock;
import org.mehr.desktop.model.functions.ChangedStockFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

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
            try (PortalAPI api = new PortalAPI()) {
                Map<String, OnlineStock> onlineStocks = api.getStocks();
                logger.info("Retrieved {} stocks.", onlineStocks.size());
                ChangedStockFinder changedStockFinder = new ChangedStockFinder();
                List<OnlineStock> changedStock = changedStockFinder.apply(records, onlineStocks);
                logger.info("{} out of {} stocks were changed after importing {} records.", changedStock.size(), onlineStocks.size(), records.size());
                api.updateStocks(changedStock);
                logger.info("Updated all stock.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
