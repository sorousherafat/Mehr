package org.mehr.desktop.model.functions;

import org.mehr.desktop.model.entities.OnSiteStock;
import org.mehr.desktop.model.entities.OnlineStock;
import org.mehr.desktop.model.repositories.IDRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class ChangedStockFinder implements BiFunction<List<OnSiteStock>, Map<String, OnlineStock>, List<OnlineStock>> {
    private final Map<String, String> idMap;

    public ChangedStockFinder() throws SQLException {
        IDRepository repository = new IDRepository();
        this.idMap = repository.readIDs();
    }

    @Override
    public List<OnlineStock> apply(List<OnSiteStock> onSiteStocks, Map<String, OnlineStock> onlineStockMap) {
        return onSiteStocks.stream()
                .map(onSiteStock -> {
                    String onlineID = idMap.get(onSiteStock.getCode());
                    OnlineStock onlineStock = onlineStockMap.get(onlineID);
                    return new StockHolder(onlineID, onlineStock.getTitle(), onlineStock.getStock(), onSiteStock.getStock());
                })
                .filter(stockHolder -> stockHolder.onlineStock != stockHolder.onSiteStock)
                .map(stockHolder -> new OnlineStock(Integer.parseInt(stockHolder.onlineID), stockHolder.onlineTitle, stockHolder.onSiteStock))
                .collect(Collectors.toList());
    }

    private static final class StockHolder {
        private final String onlineID;
        private final String onlineTitle;
        private final int onlineStock;
        private final int onSiteStock;

        private StockHolder(String onlineID, String onlineTitle, int onlineStock, int onSiteStock) {
            this.onlineID = onlineID;
            this.onlineTitle = onlineTitle;
            this.onlineStock = onlineStock;
            this.onSiteStock = onSiteStock;
        }
    }
}
