package examen.correccion.parcial2.service;

import examen.correccion.parcial2.controller.dto.EmpleadoPagoRQ;
import examen.correccion.parcial2.controller.dto.PagoRolRQ;
import examen.correccion.parcial2.model.Empleado;
import examen.correccion.parcial2.model.EmpleadoPago;
import examen.correccion.parcial2.model.Empresa;
import examen.correccion.parcial2.model.PagoRol;
import examen.correccion.parcial2.repository.EmpresaRepository;
import examen.correccion.parcial2.repository.PagoRolRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PagoRolService {

    private final PagoRolRepository pagoRolRepository;
    private final EmpresaRepository empresaRepository;

    public PagoRolService(PagoRolRepository pagoRolRepository, EmpresaRepository empresaRepository) {
        this.pagoRolRepository = pagoRolRepository;
        this.empresaRepository = empresaRepository;
    }

    public void crearPagoRol(PagoRolRQ pagoRolRQ, Integer mes){
        try{
            PagoRol pagoRolRequest = this.transformPagoRolRQ(pagoRolRQ);
            if(pagoRolRequest != null && Objects.equals(mes, pagoRolRequest.getMes())){
                this.pagoRolRepository.save(pagoRolRequest);
            }
        }catch (RuntimeException rte){
            throw new RuntimeException();
        }
    }

    public Map<String, Object> validarPagoRol(Integer mes, String ruc){
        Empresa empresa = this.empresaRepository.findByRuc(ruc);
        PagoRol pagoRol = this.pagoRolRepository.findByMesAndRucEmpresa(mes, ruc);
        if(empresa != null && pagoRol != null){
            List<Empleado> empresaEmpleados = empresa.getEmpleados();
            List<EmpleadoPago> pagoEmpleados = pagoRol.getEmpleadosPago();
            if(empresaEmpleados != null && pagoEmpleados != null ){
                for (EmpleadoPago empleadoPago : pagoEmpleados){
                    boolean empleadoValidado = false;
                    for (Empleado empleadoEmpresa : empresaEmpleados){
                        if(empleadoEmpresa.getNumeroCuenta().equals(empleadoPago.getNumeroCuenta())){
                            empleadoValidado = true;
                            break;
                        }
                    }
                    if(empleadoValidado){
                        empleadoPago.setEstado("VAL");
                    }else{
                        empleadoPago.setEstado("BAD");
                    }
                }
                pagoRol.setEmpleadosPago(pagoEmpleados);
                pagoRol.setValorReal(this.totalPagar(pagoEmpleados, 2));

                return this.requestValidacion(this.pagoRolRepository.save(pagoRol));

            }else{
                throw new RuntimeException();
            }
        }else{
            throw new RuntimeException();
        }
    }

    public PagoRol transformPagoRolRQ(PagoRolRQ pagoRolRQ){
        return PagoRol.builder()
                .mes(pagoRolRQ.getMes())
                .fechaProceso(pagoRolRQ.getFechaProceso())
                .rucEmpresa(pagoRolRQ.getRucEmpresa())
                .cuentaPrincipal((pagoRolRQ.getCuentaPrincipal()))
                .valorTotal(this.totalPagar(this.transformEmpleadosPago(pagoRolRQ.getEmpleadosPagoRQ()),1))
                .valorReal(BigDecimal.ZERO)
                .empleadosPago(this.transformEmpleadosPago(pagoRolRQ.getEmpleadosPagoRQ()))
                .build();
    }

    private List<EmpleadoPago> transformEmpleadosPago(List<EmpleadoPagoRQ> empleadoPagoRQList){
        List<EmpleadoPago> empleadoPagoList = new ArrayList<>();
        for(EmpleadoPagoRQ empleadoPagoRQ : empleadoPagoRQList){
            empleadoPagoList.add(this.transformEmpleadoPagoRQ(empleadoPagoRQ));
        }
        return empleadoPagoList;
    }

    private EmpleadoPago transformEmpleadoPagoRQ(EmpleadoPagoRQ rq){
        return EmpleadoPago.builder()
                .numeroCuenta(rq.getNumeroCuenta())
                .valor(rq.getValor())
                .estado("PEN")
                .build();
    }

    private BigDecimal totalPagar(List<EmpleadoPago> empleados, Integer opcion){
        BigDecimal suma = BigDecimal.ZERO;
        if(opcion == 1){
            for(EmpleadoPago empleadoPago : empleados){
                suma= suma.add(empleadoPago.getValor());
            }
        }else if (opcion == 2){
            for(EmpleadoPago empleadoPago : empleados){
                if(empleadoPago.getEstado().equals("VAL")){
                    suma = suma.add(empleadoPago.getValor());
                }
            }
        }
        return suma;
    }

    private Map<String, Object> requestValidacion(PagoRol pagoRol){
        Map<String, Object> request = new HashMap<>();

        request.put("MES", pagoRol.getMes());
        request.put("RUC_EMPRESA", pagoRol.getRucEmpresa());
        request.put("VALOR_TOTAL", pagoRol.getValorTotal());
        request.put("VALOR_REAL", pagoRol.getValorReal());
        request.put("TOTAL_TRANSACCIONES", this.numeroEstado(pagoRol,1));
        request.put("ERRORES", this.numeroEstado(pagoRol,2));

        return request;
    }

    private Integer numeroEstado(PagoRol pagoRol, Integer opcion){
        List<EmpleadoPago> empleados = pagoRol.getEmpleadosPago();
        Integer suma = 0;
        if(empleados != null){
            for(EmpleadoPago empleado : empleados){
                if(opcion == 1 && empleado.getEstado().equals("VAL")){
                    suma++;
                } else if (opcion == 2 && empleado.getEstado().equals("BAD")) {
                    suma++;
                }
            }
        }
        return suma;
    }
}
