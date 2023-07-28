package examen.correccion.parcial2.model;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "Empresa")
public class Empresa {
    @Id
    private String id;
    private String ruc;
    private String razonSocial;
    private String cuentaPrincipal;
    private List<Empleado> empleados;
}
