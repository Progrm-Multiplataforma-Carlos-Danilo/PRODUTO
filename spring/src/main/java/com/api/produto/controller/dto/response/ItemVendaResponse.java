package com.api.produto.controller.dto.response;

import java.math.BigDecimal;

public record ItemVendaResponse(
        String produtoId,
        String nome,
        Integer quantidade,
        BigDecimal precoUnitario
) {
}
