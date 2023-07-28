package examen.correccion.parcial2.repository;

import examen.correccion.parcial2.model.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {
    Empresa findByRuc(String ruc);
}
