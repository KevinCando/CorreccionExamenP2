package examen.correccion.parcial2.controller;

import examen.correccion.parcial2.controller.dto.EmpresaRQ;
import examen.correccion.parcial2.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/empresa-create")
    public ResponseEntity<?> createEmpresa(@RequestBody EmpresaRQ empresaRQ){
        try{
            this.empresaService.crearEmpresa(empresaRQ);
            return ResponseEntity.ok().build();
        }catch (RuntimeException rte){
            return ResponseEntity.badRequest().build();
        }
    }
}