package examen.correccion.parcial2.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "PagoRol")
public class PagoRol {
    @Id
    private String id;
    private Integer mes;
    private Date fechaProceso;
    private String rucEmpresa;
    private String cuentaPrincipal;
    private BigDecimal valorTotal;
    private BigDecimal valorReal;
    private List<EmpleadoPago> empleadosPago;
}
