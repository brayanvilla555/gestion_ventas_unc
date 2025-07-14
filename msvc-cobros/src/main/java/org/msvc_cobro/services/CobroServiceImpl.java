package org.msvc_cobro.services;

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
        //Validamos que la caja exista

        //validar que una venta solo tenga un cobro y un cobro pertenesca solo a una venta

        Caja cajaDB = cajaClientRest.cajaAbierta();
        Cobro nuevoCobro = new Cobro();
        nuevoCobro.setVentaId(ventaDTO.getVentaId());
        nuevoCobro.setCajaId(cajaDB.getId());
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
            VentaDTO ventaDTO = ventaClientRest.detalle(cobroDB.getVentaId());
            Caja caja = cajaClientRest.detalle(cobroDB.getCajaId());

            cobroDB.setEstadoCobro(EstadoCobro.COBRADO);
            cobroDB.setMetodoPago(MetodoPago.valueOf(cobro.getMetodoPago().toString()));
            cobroDB.setFechaPago(LocalDateTime.now());
            cobroDB.setObservaciones(cobroDB.getObservaciones() + " " + cobro.getObservaciones());

//            caja.setSaldoFinal(caja.getSaldoFinal()+ventaDTO.getMontoTotal());
//            cajaClientRest.actualizarMontoFinalCaja(caja, caja.getId());
            return Optional.of(cobroRepository.save(cobroDB));
        }else{
            return Optional.empty();
        }

    }

}