package com.api.produto.repository.mongo;

import com.api.produto.repository.orm.VendaOrmMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface VendaRepositoryWithMongoDB extends MongoRepository<VendaOrmMongo, String> {
    List<VendaOrmMongo> findByClienteId(String clienteId);
}
