package com.api.produto.controller.adapter;

import com.api.produto.controller.dto.request.VendaRequest;
import com.api.produto.controller.dto.response.ItemVendaResponse;
import com.api.produto.controller.dto.response.VendaResponse;
import com.api.produto.entity.ItemVenda;
import com.api.produto.entity.Venda;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class VendaControllerAdapter {

    private VendaControllerAdapter() {
    }

    public static Venda castRequest(VendaRequest request, List<ItemVenda> itensCalculados) {
        int totalItens = itensCalculados.stream().mapToInt(ItemVenda::quantidade).sum();
        BigDecimal valorTotal = itensCalculados.stream()
                .map(item -> item.precoUnitario().multiply(new BigDecimal(item.quantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Venda(
                UUID.randomUUID().toString(),
                request.clienteId(),
                itensCalculados,
                totalItens,
                valorTotal
        );
    }

    public static VendaResponse castResponse(Venda venda) {
        return new VendaResponse(
                venda.id(),
                venda.clienteId(),
                venda.itens().stream()
                        .map(item -> new ItemVendaResponse(item.produtoId(), item.nome(), item.quantidade(), item.precoUnitario()))
                        .collect(Collectors.toList()),
                venda.totalItens(),
                venda.valorTotal()
        );
    }
}
