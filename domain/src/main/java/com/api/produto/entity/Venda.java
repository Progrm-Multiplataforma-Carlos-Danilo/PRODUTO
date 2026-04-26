package com.api.produto.entity;

import java.math.BigDecimal;
import java.util.List;

public record Venda(
        String id,
        String clienteId,
        List<ItemVenda> itens,
        Integer totalItens,
        BigDecimal valorTotal
) {
}
