package org.msvc_caja.services;

import org.msvc_caja.integration.clients.CobroClientRest;
import org.msvc_caja.models.Cobro;
import org.msvc_caja.models.entity.Caja;
import org.msvc_caja.repositories.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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



//
////-------------------------------------------------------------------------------------//
////-------------------------------------------------------------------------------------//
//    //Metodos remotos
    @Override
    public List<Caja> cajaListCobros() {
        List<Caja> cajas = (List<Caja>) cajaRepository.findAll();
        for (Caja caja: cajas){
            for (Cobro cobro: cobroClientRest.listar()){
                if(cobro.getCajaId().equals(caja.getId())){
                    caja.addCobro(cobro);
                }
            }
        }
        return cajas;
    }


}
