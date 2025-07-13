package org.msvc_producto.controller;


import jakarta.validation.Valid;
import org.msvc_producto.entity.EstadoProducto;
import org.msvc_producto.entity.Producto;
import org.msvc_producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {
    @Autowired
    private ProductoService service;

    //listar todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.listarProductos());
    }

    //ver detalle por id
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Producto> productoOptional = service.buscarPorId(id); //devuelve lso productos buscacos

        return productoOptional.isPresent() ? ResponseEntity.ok(productoOptional.get()) : ResponseEntity.notFound().build();

    }

    //crear producto
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Producto producto) {
        Producto productoDB = service.guardar(producto);
        producto.setFechaActualizacion(LocalDateTime.now());
        producto.setFechaCreacion(LocalDateTime.now());
        Producto ObjetoProducto = service.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ObjetoProducto);
    }

    //Editar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Producto producto, @PathVariable Long id) {
        Optional<Producto> productoOptional = service.buscarPorId(id);
        if (productoOptional.isPresent()) {
            Producto productoDB = productoOptional.get();
            productoDB.setNombre(producto.getNombre());
            productoDB.setPrecio(producto.getPrecio());
            productoDB.setStockAlmacen(producto.getStockAlmacen());
            productoDB.setStockAlmacen(producto.getStockTienda());
            productoDB.setFechaActualizacion(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(productoDB));
        }
        return ResponseEntity.notFound().build();
    }

    //Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Producto> productoOptional = service.buscarPorId(id);
        if (productoOptional.isPresent()) {
            service.eliminar(productoOptional.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/aumentar-almacen")
    public ResponseEntity<?> aumentarStockAlmacen(@PathVariable Long id, @RequestParam int cantidad) {
        Optional<Producto> op = service.buscarPorId(id);
        if (op.isPresent()) {
            Producto producto = op.get();
            producto.setStockAlmacen(producto.getStockAlmacen() + cantidad);
            producto.setFechaActualizacion(LocalDateTime.now());
            return ResponseEntity.ok(service.guardar(producto));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/transferir-stock")
    public ResponseEntity<?> transferirStock(@PathVariable Long id, @RequestParam int cantidad) {
        Optional<Producto> op = service.buscarPorId(id);
        if (op.isPresent()) {
            Producto producto = op.get();
            if (producto.getStockAlmacen() >= cantidad) {
                producto.setStockAlmacen(producto.getStockAlmacen() - cantidad);
                producto.setStockTienda(producto.getStockTienda() + cantidad);
                producto.setFechaActualizacion(LocalDateTime.now());
                return ResponseEntity.ok(service.guardar(producto));
            } else {
                return ResponseEntity.badRequest().body("Stock en almacén insuficiente");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/actualizar-precio")
    public ResponseEntity<?> actualizarPrecio(@PathVariable Long id, @RequestParam double nuevoPrecio) {
        Optional<Producto> op = service.buscarPorId(id);
        if (op.isPresent()) {
            Producto producto = op.get();
            if (nuevoPrecio > 0) {
                producto.setPrecio(nuevoPrecio);
                producto.setFechaActualizacion(LocalDateTime.now());
                return ResponseEntity.ok(service.guardar(producto));
            } else {
                return ResponseEntity.badRequest().body("El precio debe ser mayor a 0");
            }
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}/cambiar-estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        Optional<Producto> op = service.buscarPorId(id);
        if (op.isPresent()) {
            Producto producto = op.get();
            try {
                EstadoProducto estadoEnum = EstadoProducto.valueOf(estado.toUpperCase());
                producto.setEstado(estadoEnum);
                producto.setFechaActualizacion(LocalDateTime.now());
                return ResponseEntity.ok(service.guardar(producto));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Estado inválido. Use ACTIVO o INACTIVO.");
            }
        }
        return ResponseEntity.notFound().build();
    }


}

