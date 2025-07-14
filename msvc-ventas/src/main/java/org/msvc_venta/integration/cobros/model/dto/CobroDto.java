package org.msvc_venta.integration.cobros.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CobroDto {
    private Long ventaId;

    private Double montoTotal;

    private String observaciones;
}
