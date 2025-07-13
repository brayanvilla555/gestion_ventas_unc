package org.msvc_caja.integration.clients;

import org.msvc_caja.models.Cobro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "msvc-cobros", url = "localhost:8093/cobros")
public interface CobroClientRest {
    @GetMapping("/{id}")
    Cobro detalle(Long id);
    @PostMapping()
    Cobro guardar(Cobro cobro);
    @GetMapping
    List<Cobro> listar();
}
