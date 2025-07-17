package org.msvc_cobro.integration.clients;

import org.msvc_cobro.models.Caja;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-caja", url = "http://localhost:8092/cajas")
public interface CajaClientRest {

    @GetMapping("/{id}")
    Caja detalle(@PathVariable Long id);

}