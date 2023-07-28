package examen.correccion.parcial2.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PagoRolRQ {

    private Integer mes;
    private Date fechaProceso;
    private String rucEmpresa;
    private String cuentaPrincipal;
    private List<EmpleadoPagoRQ> empleadosPagoRQ;
}
