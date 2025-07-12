package org.msvc_venta.service;

import org.msvc_venta.integration.clientes.model.dto.ClienteDto;
import org.msvc_venta.model.entity.Venta;

import java.util.List;

public interface VentaService {
    List<Venta> listVentas();

    Venta guardar(Venta venta);

    Venta editar(Long id, Venta venta);

    Venta buscarPorId(Long id);

    boolean eliminar(Long id);
}
