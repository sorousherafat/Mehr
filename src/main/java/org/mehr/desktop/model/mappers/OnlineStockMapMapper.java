package org.mehr.desktop.model.mappers;

import org.mehr.desktop.model.api.responses.StockPageResponse;
import org.mehr.desktop.model.api.responses.StockResponse;
import org.mehr.desktop.model.entities.OnlineStock;

import java.util.Map;
import java.util.stream.Collectors;

public class OnlineStockMapMapper implements Mapper<StockPageResponse, Map<String, OnlineStock>> {
    @Override
    public Map<String, OnlineStock> map(StockPageResponse input) {
        return input.getVariants().stream().map(StockResponse::toOnlineStock).collect(Collectors.toMap(OnlineStock::getIdString, onlineStock -> onlineStock));
    }
}
