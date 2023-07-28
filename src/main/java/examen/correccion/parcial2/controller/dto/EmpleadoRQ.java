package examen.correccion.parcial2.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpleadoRQ {
    private String cedula;
    private String apellidos;
    private String nombres;
    private String numeroCuenta;
}
