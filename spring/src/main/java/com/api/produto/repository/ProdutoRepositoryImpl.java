package com.api.produto.repository;

import com.api.produto.entity.Produto;
import com.api.produto.repository.adapter.ProdutoRepositoryAdapter;
import com.api.produto.repository.mongo.ProdutoRepositoryWithMongoDB;
import com.api.produto.repository.orm.ProdutoOrmMongo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {

    private final ProdutoRepositoryWithMongoDB repository;

    public ProdutoRepositoryImpl(ProdutoRepositoryWithMongoDB repository) {
        this.repository = repository;
    }

    @Override
    public Produto cadastrar(Produto produto) {
        ProdutoOrmMongo orm = ProdutoRepositoryAdapter.castEntity(produto);
        ProdutoOrmMongo ormSave = repository.save(orm);
        return ProdutoRepositoryAdapter.castOrm(ormSave);
    }

    @Override
    public Produto consultar(String id){
        Optional<ProdutoOrmMongo> orm = repository.findById(id);
        if (orm.isEmpty()) return null;
        return ProdutoRepositoryAdapter.castOrm(orm.get());
    }

    @Override
    public Produto atualizar(Produto produto){
        ProdutoOrmMongo orm = ProdutoRepositoryAdapter.castEntity(produto);
        ProdutoOrmMongo ormUpdate = repository.save(orm);
        return ProdutoRepositoryAdapter.castOrm(ormUpdate);
    }

    @Override
    public void deletar(String id) {
        repository.deleteById(id);
    }

    @Override
    public java.util.Collection<Produto> listar() {
        return repository.findAll().stream()
                .map(ProdutoRepositoryAdapter::castOrm)
                .collect(java.util.stream.Collectors.toList());
    }

}
