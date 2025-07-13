package org.msvc_cobro.models;

import java.time.LocalDateTime;
import java.util.List;

public class Venta {
    private Long id;
    private LocalDateTime fechaVenta;
    private String IDcliente;
    private List<ItemVenta> itemsVenta;
    private String estado;
    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }


    public String getIDcliente() {
        return IDcliente;
    }

    public void setIDcliente(String IDcliente) {
        this.IDcliente = IDcliente;
    }

    public List<ItemVenta> getItemsVenta() {
        return itemsVenta;
    }

    public void setItemsVenta(List<ItemVenta> itemsVenta) {
        this.itemsVenta = itemsVenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

