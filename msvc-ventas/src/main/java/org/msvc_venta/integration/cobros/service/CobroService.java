package org.msvc_venta.integration.cobros.service;

import org.msvc_venta.integration.cobros.model.dto.CobroDto;

import java.util.Optional;

public interface CobroService {
    Optional<CobroDto> registrarCobro(CobroDto cobroDto);
}
