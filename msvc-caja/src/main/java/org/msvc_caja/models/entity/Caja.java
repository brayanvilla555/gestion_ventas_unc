package org.msvc_caja.models.entity;


import jakarta.persistence.*;
import org.msvc_caja.models.CobroDTO;
import org.msvc_caja.util.EstadoCaja;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Caja")
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaApertura;

    private LocalDateTime fechaCierre;

    private EstadoCaja estado;

    private Double saldoInicial;

    private Double saldoFinal;

    private String observaciones;

    @Transient
    private List<CobroDTO> listaCobroDTOS;

    public Caja(){
        listaCobroDTOS = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public EstadoCaja getEstado() {
        return estado;
    }

    public void setEstado(EstadoCaja estado) {
        this.estado = estado;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(Double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<CobroDTO> getListaCobros() {
        return listaCobroDTOS;
    }

    public void setListaCobros(List<CobroDTO> listaCobroDTOS) {
        this.listaCobroDTOS = listaCobroDTOS;
    }

    public void addCobro(CobroDTO cobroDTO){
        listaCobroDTOS.add(cobroDTO);
    }
}