package org.msvc_venta.integration.cobros.client;

import org.msvc_venta.integration.cobros.model.dto.CobroDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(
        name = "msvc-cobros",
        url = "localhost:8093/"
)
public interface CobroClient {
    @PostMapping("cobros")
    Optional<CobroDto> registrarCobro(@RequestBody CobroDto cobroDto);
}
