package com.api.produto.repository.orm;

import java.math.BigDecimal;

public record ItemVendaOrm(
        String produtoId,
        String nome,
        Integer quantidade,
        BigDecimal precoUnitario
) {}
