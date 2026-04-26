package com.api.produto.repository;

import com.api.produto.entity.Produto;

public interface ProdutoRepository {
    Produto cadastrar(Produto prd);
    Produto consultar(String id);
    Produto atualizar(Produto prd);
    void deletar(String id);
    java.util.Collection<Produto> listar();
}
