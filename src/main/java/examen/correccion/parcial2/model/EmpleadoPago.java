package examen.correccion.parcial2.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class EmpleadoPago {
    private String numeroCuenta;
    private BigDecimal valor;
    private String estado;
}
