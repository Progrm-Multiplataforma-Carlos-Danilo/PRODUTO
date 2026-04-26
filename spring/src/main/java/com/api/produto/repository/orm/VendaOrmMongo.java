package com.api.produto.repository.orm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "vendas")
public record VendaOrmMongo(
        @Id String id,
        String clienteId,
        List<ItemVendaOrm> itens,
        Integer totalItens,
        BigDecimal valorTotal
) {
}


