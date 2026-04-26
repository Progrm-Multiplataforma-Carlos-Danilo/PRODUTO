package com.api.produto.repository.adapter;

import com.api.produto.entity.ItemVenda;
import com.api.produto.entity.Venda;
import com.api.produto.repository.orm.VendaOrmMongo;

import java.util.stream.Collectors;

public class VendaRepositoryAdapter {

    private VendaRepositoryAdapter() {
    }

    public static Venda castOrm(VendaOrmMongo orm) {
        return new Venda(
                orm.id(),
                orm.clienteId(),
                orm.itens().stream()
                        .map(item -> new ItemVenda(item.produtoId(), item.nome(), item.quantidade(), item.precoUnitario()))
                        .collect(Collectors.toList()),
                orm.totalItens(),
                orm.valorTotal()
        );
    }

    public static VendaOrmMongo castEntity(Venda entity) {
        // O ORM record está definido no mesmo arquivo do VendaOrmMongo, mas os records internos não são públicos fora do pacote.
        // Na verdade, eu defini o ItemVendaOrm no mesmo arquivo, então ele é acessível se eu usar o nome correto.
        // Mas como é um record no mesmo pacote, está ok.
        return new VendaOrmMongo(
                entity.id(),
                entity.clienteId(),
                entity.itens().stream()
                        .map(item -> new com.api.produto.repository.orm.ItemVendaOrm(item.produtoId(), item.nome(), item.quantidade(), item.precoUnitario()))
                        .collect(Collectors.toList()),
                entity.totalItens(),
                entity.valorTotal()
        );
    }
}
