package org.msvc_venta.controller;

import org.msvc_venta.model.entity.Venta;
import org.msvc_venta.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> listVentas() {
        return ResponseEntity.ok(ventaService.listVentas());
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(
            @RequestBody Venta venta
    ) {
        Venta guardarventa = ventaService.guardar(venta);

        if(guardarventa == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(guardarventa);
    }

    @PutMapping("/edita/{id}")
    public ResponseEntity<?> editar(
            @PathVariable Long id,
            @RequestBody Venta venta
    ) {
        Venta editarVenta = ventaService.editar(id, venta);

        if(editarVenta == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(editarVenta);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(
            @PathVariable Long id
    ) {
        Venta venta = ventaService.buscarPorId(id);
        if(venta != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(venta);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Boolean> eliminar(
            @PathVariable Long id
    ) {
        boolean eliminar = ventaService.eliminar(id);
        if(eliminar) {
            return ResponseEntity.ok(eliminar);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
