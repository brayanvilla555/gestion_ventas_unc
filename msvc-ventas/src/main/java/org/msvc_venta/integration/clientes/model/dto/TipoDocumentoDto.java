package org.msvc_venta.integration.clientes.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumentoDto {
    private String nombre;
    private String abreviatura;
}
