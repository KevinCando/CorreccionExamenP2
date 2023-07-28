package examen.correccion.parcial2.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EmpleadoPagoRQ {
    private String numeroCuenta;
    private BigDecimal valor;
}