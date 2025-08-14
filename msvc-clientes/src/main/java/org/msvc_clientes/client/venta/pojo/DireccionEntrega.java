package org.msvc_clientes.client.venta.pojo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DireccionEntrega {
    private String referencia;

    private String calle;

    private String ciudad;

    private String departamento;

    private String codigoPostal;
}
