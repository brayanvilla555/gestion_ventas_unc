package org.msvc_producto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity
@Table(name="productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nombre;
    private String categoria;
    @NotNull
    @Positive
    private Double precio;
    private String imagen;
    @NotNull
    @Min(0)
    private Integer stockTienda;
    @NotNull
    @Min(0)
    private Integer stockAlmacen;

    @Enumerated(EnumType.STRING)
    private EstadoProducto estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getStockTienda() {
        return stockTienda;
    }

    public void setStockTienda(Integer stockTienda) {
        this.stockTienda = stockTienda;
    }

    public Integer getStockAlmacen() {
        return stockAlmacen;
    }

    public void setStockAlmacen(Integer stockAlmacen) {
        this.stockAlmacen = stockAlmacen;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public void aumentarStockAlmacen(int cantidad) {
        if (cantidad > 0) {
            if (this.stockAlmacen == null) {
                this.stockAlmacen = 0;
            }
            this.stockAlmacen += cantidad;
        }
    }

    public void disminuirStockTienda(int cantidad) {
        if (cantidad > 0) {
            if (this.stockTienda == null) {
                this.stockTienda = 0;
            }
            if (this.stockTienda >= cantidad) {
                this.stockTienda -= cantidad;
            }
        }
    }

    public void transferirStock(int cantidad) {
        if (cantidad > 0) {
            if (this.stockAlmacen == null) {
                this.stockAlmacen = 0;
            }
            if (this.stockTienda == null) {
                this.stockTienda = 0;
            }
            if (this.stockAlmacen >= cantidad) {
                this.stockAlmacen -= cantidad;
                this.stockTienda += cantidad;
            }
        }
    }

    public void actualizarEstado(EstadoProducto nuevoEstado) {
        if (nuevoEstado != null) {
            this.estado = nuevoEstado;
        }
    }

    public void actualizarPrecio(double nuevoPrecio) {
        if (nuevoPrecio > 0) {
            this.precio = nuevoPrecio;
        }
    }
    @PrePersist
    public void prePersist() {
        if (this.estado == null) {
            this.estado = EstadoProducto.ACTIVO; // Asignar estado por defecto
        }
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

}
