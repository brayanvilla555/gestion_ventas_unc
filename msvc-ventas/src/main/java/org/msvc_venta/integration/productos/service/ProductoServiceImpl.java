package org.msvc_venta.integration.productos.service;

import org.msvc_venta.integration.productos.client.ProductoClient;
import org.msvc_venta.integration.productos.model.dto.ProductoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoClient productoClient;

    public ProductoDto existeProducto(long idProducto){
        return productoClient.buscarProductoPorId(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("El producto con el id: " + idProducto + " no existe"));
    }
}
