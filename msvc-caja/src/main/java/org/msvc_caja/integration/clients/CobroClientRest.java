package org.msvc_caja.integration.clients;

import org.msvc_caja.models.Cobro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-cobros", url = "localhost:8093/cobros")
public interface CobroClientRest {
    @GetMapping("/{id}")
    Cobro detalle(@PathVariable Long id);


    @GetMapping
    List<Cobro> listar();


    @PutMapping("/cobrar")
    void cobrar(@RequestBody Cobro cobro);
}
