package com.api.produto.repository;

import com.api.produto.entity.Venda;
import com.api.produto.repository.adapter.VendaRepositoryAdapter;
import com.api.produto.repository.mongo.VendaRepositoryWithMongoDB;
import com.api.produto.repository.orm.VendaOrmMongo;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VendaRepositoryImpl implements VendaRepository {

    private final VendaRepositoryWithMongoDB repository;

    public VendaRepositoryImpl(VendaRepositoryWithMongoDB repository) {
        this.repository = repository;
    }

    @Override
    public Venda cadastrar(Venda venda) {
        VendaOrmMongo orm = VendaRepositoryAdapter.castEntity(venda);
        VendaOrmMongo ormSave = repository.save(orm);
        return VendaRepositoryAdapter.castOrm(ormSave);
    }

    @Override
    public Venda consultarPorId(String id) {
        return repository.findById(id)
                .map(VendaRepositoryAdapter::castOrm)
                .orElse(null);
    }

    @Override
    public Collection<Venda> consultarPorCliente(String clienteId) {
        List<VendaOrmMongo> vendasDoCliente = repository.findByClienteId(clienteId);
        return vendasDoCliente.stream()
                .map(VendaRepositoryAdapter::castOrm)
                .collect(Collectors.toList());
    }
}
