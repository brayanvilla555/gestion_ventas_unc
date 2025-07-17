package org.msvc_cobro.services;

import jakarta.validation.constraints.Negative;
import org.msvc_cobro.integration.clients.CajaClientRest;
import org.msvc_cobro.integration.clients.VentaClientRest;
import org.msvc_cobro.models.Caja;
import org.msvc_cobro.models.VentaDTO;
import org.msvc_cobro.models.entity.Cobro;
import org.msvc_cobro.repositories.CobroRepository;
import org.msvc_cobro.utils.EstadoCobro;
import org.msvc_cobro.utils.MetodoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CobroServiceImpl implements CobroService{

    @Autowired
    private CobroRepository cobroRepository;
    @Autowired
    private VentaClientRest ventaClientRest;
    @Autowired
    private CajaClientRest cajaClientRest;

    @Override
    public Cobro guardar(Cobro cobro) {
        return cobroRepository.save(cobro);
    }

    @Override
    public List<Cobro> listar() {
        return (List<Cobro>) cobroRepository.findAll();
    }

    @Override
    public Optional<Cobro> porId(Long id) {
        return cobroRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        cobroRepository.deleteById(id);
    }




    //Metodos remotos
    @Override
    public Optional<Cobro> generarCobro(VentaDTO ventaDTO) {
        // validamos que la venta exista
        VentaDTO ventaDB = ventaClientRest.detalle(ventaDTO.getVentaId());

        //validar que una venta solo tenga un cobro y un cobro pertenesca solo a una venta
        Cobro nuevoCobro = new Cobro();
        nuevoCobro.setVentaId(ventaDTO.getVentaId());
        nuevoCobro.setMontoTotal(ventaDTO.getMontoTotal());
        nuevoCobro.setEstadoCobro(EstadoCobro.valueOf("PENDIENTE"));
        nuevoCobro.setObservaciones(ventaDTO.getObservaciones());
        //private MetodoPago metodoPago;
        //private LocalDateTime fechaPago;
        //private String observaciones;
        return Optional.of(cobroRepository.save(nuevoCobro));
    }


    @Override
    public Optional<Cobro> cobrar(Cobro cobro) {
        Optional<Cobro> cobroOp= cobroRepository.findById(cobro.getId());
        if(cobroOp.isPresent()){
            Cobro cobroDB = cobroOp.get();
            //verificamos que la caja exista
            Caja caja = cajaClientRest.detalle(cobro.getCajaId());
            cobroDB.setCajaId(cobro.getCajaId());
            if (!EstadoCobro.PENDIENTE.equals(cobroDB.getEstadoCobro())) {
                throw new IllegalStateException("El cobro ya fue procesado previamente.");
            }
            cobroDB.setEstadoCobro(EstadoCobro.COBRADO);
            cobroDB.setMetodoPago(MetodoPago.valueOf(cobro.getMetodoPago().toString()));
            cobroDB.setFechaPago(LocalDateTime.now());
            cobroDB.setObservaciones(cobroDB.getObservaciones() + " " + cobro.getObservaciones());
            return Optional.of(cobroRepository.save(cobroDB));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public List<Cobro> cobrosPorCobrar() {
        List<Cobro> cobrosPorCobrar = new ArrayList<>();
        for (Cobro cobro: cobroRepository.findAll()){
            if(!cobro.getEstadoCobro().equals(EstadoCobro.COBRADO)){
               cobrosPorCobrar.add(cobro);
            }
        }
        return cobrosPorCobrar;
     }

}