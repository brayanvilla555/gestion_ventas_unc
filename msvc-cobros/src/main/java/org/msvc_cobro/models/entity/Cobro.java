package org.msvc_cobro.models.entity;


import jakarta.persistence.*;
import org.msvc_cobro.utils.EstadoCobro;
import org.msvc_cobro.utils.MetodoPago;

import java.time.LocalDateTime;

@Entity
@Table(name = "cobros")
public class Cobro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "caja_id", unique = true)
    private Long cajaId;

    @Column(name = "venta_id", unique = true)
    private Long ventaId;

    private Double montoTotal;

    private MetodoPago metodoPago;

    private EstadoCobro estadoCobro;

    private LocalDateTime fechaPago;

    private String observaciones;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoCobro getEstadoCobro() {
        return estadoCobro;
    }

    public void setEstadoCobro(EstadoCobro estadoCobro) {
        this.estadoCobro = estadoCobro;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getObservaciones() {
        return observaciones;
    }


    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getCajaId() {
        return cajaId;
    }

    public void setCajaId(Long cajaId) {
        this.cajaId = cajaId;
    }
}