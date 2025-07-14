package org.msvc_venta.integration.clientes.service;

import org.msvc_venta.integration.clientes.client.ClienteClient;
import org.msvc_venta.integration.clientes.model.dto.ClienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteClient clienteClient;

    public ClienteDto existeCliente(long idCliente){
        return clienteClient.buscarClientePorId(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con el id: " + idCliente + " no existe"));
    }

}
