package org.msvc_cobro.controllers;


import feign.FeignException;
import org.msvc_cobro.models.VentaDTO;
import org.msvc_cobro.models.entity.Cobro;
import org.msvc_cobro.services.CobroService;
import org.msvc_cobro.utils.EstadoCobro;
import org.msvc_cobro.utils.MetodoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cobros")
public class CobroController {
    @Autowired
    private CobroService cobroService;


    @GetMapping
    public ResponseEntity<List<Cobro>> listar(){
        return ResponseEntity.ok(cobroService.listar());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cobro cobro){
        return ResponseEntity.status(HttpStatus.CREATED).body(cobroService.guardar(cobro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Cobro> cobroop = cobroService.porId(id);
        if(cobroop.isPresent()){
            return ResponseEntity.ok(cobroop.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Cobro cobro, @PathVariable Long id){
        Optional<Cobro> cobroOp= cobroService.porId(id);
        if (cobroOp.isPresent()) {
            Cobro cobroDB = cobroOp.get();

            cobroDB.setVentaId(cobro.getVentaId());
            cobroDB.setEstadoCobro(EstadoCobro.valueOf(cobro.getEstadoCobro().toString()));
            cobroDB.setMontoTotal(cobro.getMontoTotal());
            cobroDB.setMetodoPago(MetodoPago.valueOf(cobro.getMetodoPago().toString()));
            cobroDB.setFechaPago(cobro.getFechaPago());
            cobroDB.setObservaciones(cobro.getObservaciones());

            //Falta validar que el id de venta exista y tambien el id de caja, aunque esto se hace en este mismo microservico


            return ResponseEntity.ok(cobroService.guardar(cobroDB));
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Cobro> cobroOp = cobroService.porId(id);
        if(cobroOp.isPresent()){
            cobroService.eliminar(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }



    //MetodosRemotos para consultar si existe la venta y la caja
    @PostMapping("/generarCobro")
    public ResponseEntity<?> generarCobro(@RequestBody VentaDTO ventaDTO){
        Optional<Cobro> cobroOp;
        try{
            cobroOp = cobroService.generarCobro(ventaDTO);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("Mensaje",
                    "No existen la venta o caja proporcionado" + e.getMessage()));
        }
        if(cobroOp.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(cobroOp.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Para cobrar una venta el cliente va a caja con un ticket de venta que tiene el id de venta+
    //aqui se actualiza el monto final de una caja
    @PutMapping("/cobrar")
    public ResponseEntity<?> cobrar(@RequestBody Cobro cobro){
        Optional<Cobro> cobroOp;
        try {
            cobroOp = cobroService.cobrar(cobro);
        }catch (FeignException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("Mensaje", "No existe la caja proporcionada" + e.getMessage()));
        }
        if(cobroOp.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(cobroOp.get());
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/porcobrar")
    public ResponseEntity<List<Cobro>> listaCobrosPorCobrar(){
        return  ResponseEntity.ok(cobroService.cobrosPorCobrar());
    }

    //cajas con lista de cobros

}
