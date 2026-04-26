package com.api.produto.controller.dto.request;

public record ItemVendaRequest(
        String produtoId,
        Integer quantidade
) {
}
