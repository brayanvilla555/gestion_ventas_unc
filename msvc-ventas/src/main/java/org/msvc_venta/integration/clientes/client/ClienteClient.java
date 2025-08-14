package org.msvc_venta.integration.clientes.client;

import org.msvc_venta.integration.clientes.model.dto.ClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(
        name = "msvc-ventas",
        url = "localhost:8090/msvc_clientes/api/v1/"
)
public interface ClienteClient {
    @GetMapping("/clientes/buscar/{id}")
    Optional<ClienteDto> buscarClientePorId(@PathVariable Long id);

    @PostMapping("/clientes/guardar")
    Optional<ClienteDto> guardarCliente(ClienteDto clienteDto);
}
