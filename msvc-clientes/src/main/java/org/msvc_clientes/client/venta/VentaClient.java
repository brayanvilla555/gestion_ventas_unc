package org.msvc_clientes.client.venta;

import org.msvc_clientes.client.venta.pojo.Venta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(
        name = "msvc-ventas",
        url = "localhost:8091/msvc_ventas/api/v1"
)
public interface VentaClient {
    @GetMapping("/ventas/cliente/{idCliente}")
    Optional<List<Venta>> listarVentasPorCliente(@PathVariable Long idCliente);
}
