package com.api.produto.entity;

import java.math.BigDecimal;

public record ItemVenda(
        String produtoId,
        String nome,
        Integer quantidade,
        BigDecimal precoUnitario
) {
}
