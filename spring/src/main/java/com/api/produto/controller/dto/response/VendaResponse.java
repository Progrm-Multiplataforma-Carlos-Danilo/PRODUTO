package com.api.produto.controller.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record VendaResponse(
        String id,
        String clienteId,
        List<ItemVendaResponse> itens,
        Integer totalItens,
        BigDecimal valorTotal
) {
}
