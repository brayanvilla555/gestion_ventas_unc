package org.msvc_venta.integration.clientes.service;

import org.msvc_venta.integration.clientes.model.dto.ClienteDto;

public interface ClienteService {
    ClienteDto existeCliente(long idCliente);
}
