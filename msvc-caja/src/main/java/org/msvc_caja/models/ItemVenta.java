package org.msvc_caja.models;

import jakarta.persistence.Column;

public class ItemVenta {
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private Double precio;
    private Double subTotal;

}
