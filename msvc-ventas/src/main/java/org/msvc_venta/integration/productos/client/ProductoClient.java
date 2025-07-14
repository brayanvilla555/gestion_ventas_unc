package org.msvc_venta.integration.productos.client;

import org.msvc_venta.integration.productos.model.dto.ProductoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@FeignClient(
        name = "msvc-productos",
        url = "localhost:8070/api/"
)
public interface ProductoClient {
    @GetMapping("producto/{id}")
    Optional<ProductoDto> buscarProductoPorId(@PathVariable Long id);
}
