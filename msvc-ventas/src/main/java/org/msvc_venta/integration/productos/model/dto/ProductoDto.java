package org.msvc_venta.integration.productos.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {
    private Long id;
    private String nombre;
    private String categoria;
    private Double precio;
    private String imagen;
    private Integer stockTienda;
    private Integer stockAlmacen;
    private String estado;
}
