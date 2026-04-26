package com.api.produto.repository;

import com.api.produto.entity.Venda;
import java.util.Collection;

public interface VendaRepository {
    Venda cadastrar(Venda venda);
    Venda consultarPorId(String id);
    java.util.Collection<Venda> consultarPorCliente(String clienteId);
}
