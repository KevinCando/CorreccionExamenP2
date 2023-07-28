package examen.correccion.parcial2.repository;

import examen.correccion.parcial2.model.PagoRol;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PagoRolRepository extends MongoRepository<PagoRol, String> {
    PagoRol findByMesAndRucEmpresa(Integer mes, String rucEmpresa);
}
