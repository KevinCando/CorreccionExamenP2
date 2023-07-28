package examen.correccion.parcial2.service;

import examen.correccion.parcial2.controller.dto.EmpleadoRQ;
import examen.correccion.parcial2.controller.dto.EmpresaRQ;
import examen.correccion.parcial2.model.Empleado;
import examen.correccion.parcial2.model.Empresa;
import examen.correccion.parcial2.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public void crearEmpresa(EmpresaRQ empresaRQ){
        try{
            Empresa empresaRequest = this.transformEmpresaRQ(empresaRQ);
            Empresa empresa =this.empresaRepository.findByRuc(empresaRequest.getRuc());
            if(empresa == null){
                this.empresaRepository.save(empresaRequest);
            }
        }catch (RuntimeException rte){
            throw new RuntimeException();
        }
    }


    public Empresa transformEmpresaRQ(EmpresaRQ rq){
        return Empresa.builder()
                .ruc(rq.getRuc())
                .razonSocial(rq.getRazonSocial())
                .cuentaPrincipal(rq.getCuentaPrincipal())
                .empleados(this.transformEmpleados(rq.getEmpleados()))
                .build();
    }

    private List<Empleado> transformEmpleados(List<EmpleadoRQ> empleadosRQ){
        List<Empleado> empleadosList = new ArrayList<>();
        for(EmpleadoRQ empleadoRQ : empleadosRQ){
            empleadosList.add(this.transformEmpleadoRQ(empleadoRQ));
        }
        return empleadosList;
    }

    private Empleado transformEmpleadoRQ(EmpleadoRQ rq){
        return Empleado.builder()
                .cedula(rq.getCedula())
                .apellidos(rq.getApellidos())
                .nombres(rq.getNombres())
                .numeroCuenta(rq.getNumeroCuenta())
                .build();
    }
}
