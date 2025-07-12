package org.msvc_venta.integration.clientes.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {
    private Long id;

    private TipoDocumentoDto tipoDocumento;

    private String nombre;

    private String apellido;

    private String correo;

    private String telefono;

    private String numeroIdentificacion;

    private Boolean estado;
}
