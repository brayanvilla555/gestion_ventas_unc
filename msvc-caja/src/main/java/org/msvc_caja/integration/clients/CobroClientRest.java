package org.msvc_caja.integration.clients;

import org.msvc_caja.models.CobroDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-cobros", url = "localhost:8093/cobros")
public interface CobroClientRest {
    @GetMapping("/{id}")
    CobroDTO detalle(@PathVariable Long id);


    @GetMapping
    List<CobroDTO> listar();


    @PutMapping("/cobrar")
    void cobrar(@RequestBody CobroDTO cobro);
}
