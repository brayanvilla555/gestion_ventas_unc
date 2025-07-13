package org.msvc_cobro.services;

import org.msvc_cobro.integration.clients.CajaClientRest;
import org.msvc_cobro.integration.clients.VentaClientRest;
import org.msvc_cobro.models.Caja;
import org.msvc_cobro.models.Venta;
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
    public Optional<Cobro> generarCobro(Venta venta, Long idCaja) {
        // validamos que la venta exista
        Venta ventaDB = ventaClientRest.detalle(venta.getId());
        //Validamos que la caja exista
        Caja cajaDB = cajaClientRest.detalle(idCaja);

        if(cajaDB.getEstado().toString().equals("ABIERTA")){
            Cobro nuevoCobro = new Cobro();
            nuevoCobro.setVentaId(ventaDB.getId());
            nuevoCobro.setCajaId(cajaDB.getId());
            nuevoCobro.setMontoTotal(ventaDB.getTotal());
            nuevoCobro.setEstadoCobro(EstadoCobro.valueOf("PENDIENTE"));

            //private MetodoPago metodoPago;
            //private LocalDateTime fechaPago;
            //private String observaciones;

            return Optional.of(cobroRepository.save(nuevoCobro));
        }else{
            return Optional.empty();
        }
    }


    @Override
    public Optional<Cobro> cobrar(Cobro cobro) {
        Optional<Cobro> cobroOp= cobroRepository.findById(cobro.getId());
        if(cobroOp.isPresent()){
            Cobro cobroDB = cobroOp.get();
            Venta venta = ventaClientRest.detalle(cobroDB.getVentaId());
            Caja caja = cajaClientRest.detalle(cobroDB.getCajaId());

            cobroDB.setEstadoCobro(EstadoCobro.valueOf(cobro.getEstadoCobro().toString()));
            cobroDB.setMetodoPago(MetodoPago.valueOf(cobro.getMetodoPago().toString()));
            cobroDB.setFechaPago(LocalDateTime.now());
            cobroDB.setObservaciones(cobro.getObservaciones());

            caja.setSaldoFinal(caja.getSaldoFinal()+cobro.getMontoTotal());
            cajaClientRest.actualizarMontoFinalCaja(caja, caja.getId());
            return Optional.of(cobroRepository.save(cobroDB));
        }else{
            return Optional.empty();
        }

    }

}