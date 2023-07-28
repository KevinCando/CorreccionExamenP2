package examen.correccion.parcial2.controller;

import examen.correccion.parcial2.controller.dto.PagoRolRQ;
import examen.correccion.parcial2.service.PagoRolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/pago-rol")
public class PagoRolController {
    private final PagoRolService pagoRolService;

    public PagoRolController(PagoRolService pagoRolService) {
        this.pagoRolService = pagoRolService;
    }

    @PostMapping("/rol-create/{mes}")
    public ResponseEntity<?> createPagoRol(@RequestBody PagoRolRQ pagoRolRQ,
                                           @PathVariable(name = "mes") Integer mes){
        try{
            this.pagoRolService.crearPagoRol(pagoRolRQ, mes);
            return ResponseEntity.ok().build();
        }catch (RuntimeException rte){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/rol-validate")
    public ResponseEntity<Map<String, Object>> validarPagoRol(@RequestParam Integer mes,
                                                              @RequestParam String ruc
    ) {
        try {
            return ResponseEntity.ok(this.pagoRolService.validarPagoRol(mes, ruc));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
