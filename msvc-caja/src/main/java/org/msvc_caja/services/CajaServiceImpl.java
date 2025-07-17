package org.msvc_caja.services;

import org.msvc_caja.integration.clients.CobroClientRest;
import org.msvc_caja.models.Cobro;
import org.msvc_caja.models.entity.Caja;
import org.msvc_caja.repositories.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CajaServiceImpl implements CajaService{

    @Autowired
    private CajaRepository cajaRepository;
    @Autowired
    private CobroClientRest cobroClientRest;

    @Override
    public Caja guardar(Caja caja) {
        return cajaRepository.save(caja);
    }

    @Override
    public List<Caja> listar() {
        return (List<Caja>) cajaRepository.findAll();
    }

    @Override
    public Optional<Caja> porId(Long id) {
        return cajaRepository.findById(id);
    }

    @Override
    public void eliminar(Long id) {
        cajaRepository.deleteById(id);
    }

    @Override
    public Optional<Caja> cajaAbierta() {
        List<Caja> cajas = (List<Caja>) cajaRepository.findAll();
        if (!cajas.isEmpty()){
            //La ultima caja es la que siempre va estar abierta
            return Optional.of(cajas.get(cajas.size()-1));
        }
        return Optional.empty();
    }


//
////-------------------------------------------------------------------------------------//
////-------------------------------------------------------------------------------------//
//    //Metodos remotos
    @Override
    public List<Caja> cajaListCobros() {
        List<Caja> cajas = (List<Caja>) cajaRepository.findAll();
        for (Caja caja: cajas){
            for (Cobro cobroDTO : cobroClientRest.listar()){
                if(cobroDTO.getCajaId() != null && cobroDTO.getCajaId().equals(caja.getId()) ){
                    caja.addCobro(cobroDTO);
                }
            }
        }
        return cajas;
    }


    @Override
    public Double calcularSaldo(Long id) {
        List<Cobro> cobros = cobroClientRest.listar();
        Optional<Caja> cajaOp = cajaRepository.findById(id);
        Double monto= 0.0;
        if (cajaOp.isPresent()){

            for (Cobro cobro: cobros){
                if(cobro.getCajaId() != null && cobro.getCajaId() == id){
                    monto += cobro.getMontoTotal();
                }
            }
            monto += cajaOp.get().getSaldoInicial();
        }

        return monto;
    }



    @Override
    public Optional<Cobro> cobrar(Cobro cobroDTO) {
        Cobro cobro = cobroClientRest.detalle(cobroDTO.getId());
        cobro.setCajaId(cobroDTO.getCajaId());
        cobro.setMetodoPago(cobroDTO.getMetodoPago());
        cobro.setObservaciones(cobroDTO.getObservaciones());
        cobroClientRest.cobrar(cobro);

        //validar que un cobro solo se cancele una ves, de lo contrario enviar error:

        Optional<Caja> caja = cajaRepository.findById(cobroDTO.getCajaId());
        if (caja.isPresent()){
            Caja cajaDB = caja.get();
            cajaDB.setSaldoFinal(cajaDB.getSaldoFinal()+cobro.getMontoTotal());
            cajaRepository.save(cajaDB);
        }
        return Optional.of(cobro);
    }
}
