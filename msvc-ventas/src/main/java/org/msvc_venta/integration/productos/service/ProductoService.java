package org.msvc_venta.integration.productos.service;

import org.msvc_venta.integration.productos.model.dto.ProductoDto;

public interface ProductoService {
    ProductoDto existeProducto(long idProducto);
    ProductoDto actualizarStockTienda(long idProducto, int cantidad);
}
