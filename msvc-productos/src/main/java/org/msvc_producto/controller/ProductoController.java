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
        return ResponseEntity.status(HttpStatus.CREATED).body(productoDB);
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
            productoDB.setStockTienda(producto.getStockTienda());
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

    //aumentar stock de almacen
    @PutMapping("/{id}/aumentar-stock-almacen")
    public ResponseEntity<?> aumentarStockAlmacen(@PathVariable Long id, @RequestParam int cantidad) {
        Optional<Producto> productoActualizado = service.aumentarStockAlmacen(id, cantidad);
        if (productoActualizado.isPresent()) {
            return ResponseEntity.ok(productoActualizado.get());
        }
        return ResponseEntity.notFound().build();
    }

    //diminuir stock de tienda
    @PutMapping("/{id}/disminuir-stock-tienda")
    public ResponseEntity<?> disminuirStockTienda(@PathVariable Long id, @RequestParam int cantidad) {
        Optional<Producto> productoActualizado = service.disminuirStockTienda(id, cantidad);
        if (productoActualizado.isPresent()) {
            return ResponseEntity.ok(productoActualizado.get());
        }
        return ResponseEntity.notFound().build();
    }

    //transferir stock de tienda
    @PutMapping("/{id}/transferir-stock")
    public ResponseEntity<?> transferirStock(@PathVariable Long id, @RequestParam int cantidad) {
        Optional<Producto> productoTransferido= service.transferirStockTienda(id, cantidad);
        if(productoTransferido.isPresent()) {
            return ResponseEntity.ok(productoTransferido.get());
        }
            return ResponseEntity.notFound().build();
    }
    // Actualizar estado del producto
    @PutMapping("/{id}/actualizar-estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam EstadoProducto estado) {
        Optional<Producto> productoActualizado = service.actualizarEstado(id, estado);
        if (productoActualizado.isPresent()) {
            return ResponseEntity.ok(productoActualizado.get());
        }
        return ResponseEntity.notFound().build();
    }
    // Actualizar precio del producto
    @PutMapping("/{id}/actualizar-precio")
    public ResponseEntity<?> actualizarPrecio(@PathVariable Long id, @RequestParam double precio) {
        Optional<Producto> productoActualizado = service.actualizarPrecio(id, precio);
        if (productoActualizado.isPresent()) {
            return ResponseEntity.ok(productoActualizado.get());
        }
        return ResponseEntity.notFound().build();
    }

}

