package org.msvc_clientes.client.venta.pojo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Venta {
    private Long id;

    private LocalDateTime fechaVenta;

    private DireccionEntrega direccion;

    private Double total;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
