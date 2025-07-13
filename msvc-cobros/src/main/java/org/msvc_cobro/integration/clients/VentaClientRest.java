package org.msvc_cobro.integration.clients;

import org.msvc_cobro.models.Venta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-ventas", url = "http://localhost:8091/msvc_productos/api/v1/ventas")
public interface VentaClientRest {
    @GetMapping("/buscar/{id}")
    Venta detalle(@PathVariable Long id);

}