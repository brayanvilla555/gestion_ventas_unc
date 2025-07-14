package org.msvc_caja.controllers;



import feign.FeignException;
import org.msvc_caja.models.CobroDTO;
import org.msvc_caja.models.entity.Caja;
import org.msvc_caja.services.CajaService;
import org.msvc_caja.util.EstadoCaja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cajas")
public class CajaController {

    @Autowired
    private CajaService cajaService;

    @GetMapping
    public ResponseEntity<List<Caja>> listar(){
        return ResponseEntity.ok(cajaService.listar());
    }
    @PostMapping
    public  ResponseEntity<?> guardar(@RequestBody Caja caja){
        return ResponseEntity.status(HttpStatus.CREATED).body(cajaService.guardar(caja));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Caja> cajaOp = cajaService.porId(id);
        if(cajaOp.isPresent()){
            return ResponseEntity.ok(cajaOp.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Caja caja,@PathVariable Long id){
        Optional<Caja> cajaOp = cajaService.porId(id);
        if(cajaOp.isPresent()){
            Caja cajaDB = cajaOp.get();
            cajaDB.setFechaApertura(caja.getFechaApertura());
            cajaDB.setFechaCierre(caja.getFechaCierre());
            cajaDB.setEstado(EstadoCaja.valueOf(caja.getEstado().toString()));
            cajaDB.setSaldoInicial(caja.getSaldoInicial());
            cajaDB.setSaldoFinal(caja.getSaldoFinal());
            cajaDB.setObservaciones(caja.getObservaciones());
            // esto faltaria, pero no podemos editar aquii esto de quitar una lista de cobros, los cobros
            // una ves creados no pueden ser eliminados, solo se cambia su estado
            //private List<Cobro> listaCobros;
            return ResponseEntity.ok(cajaService.guardar(cajaDB));

        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/cajaabierta")
    public ResponseEntity<?> retornarCajaAbierta(){
        Optional<Caja> cajaOp= cajaService.cajaAbierta();
        if (cajaOp.isPresent()){
            return  ResponseEntity.ok(cajaOp.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/cajaConCobros")
    public ResponseEntity<?> cajaConListaCobros(){
        try {
            return ResponseEntity.ok(cajaService.cajaListCobros());
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("Mensaje", "No existen cobros" + e.getMessage()));
        }
    }

    @PutMapping("/cobrar")
    public ResponseEntity<?> cobrar(@RequestBody CobroDTO cobroDTO){
        try{
            return ResponseEntity.ok(cajaService.cobrar(cobroDTO));
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("Mensaje", "No existe ese cobroYOEL" + e.getMessage()));
        }
    }

}
