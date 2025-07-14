package org.msvc_producto.service;

import org.msvc_producto.entity.EstadoProducto;
import org.msvc_producto.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listarProductos();
    Optional<Producto> buscarPorId(Long id);
    Producto guardar(Producto producto);
    void eliminar(Long id);
    Optional<Producto> aumentarStockAlmacen(Long id, int cantidad);
    Optional<Producto> disminuirStockTienda(Long id, int cantidad);
    Optional<Producto> transferirStockTienda(Long id, int cantidad);
    Optional<Producto> actualizarEstado(Long id, EstadoProducto nuevoEstado);
    Optional<Producto> actualizarPrecio(Long id, double nuevoPrecio);
}
