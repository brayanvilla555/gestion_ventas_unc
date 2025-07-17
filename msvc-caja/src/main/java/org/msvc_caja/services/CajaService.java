package org.msvc_caja.services;

import org.msvc_caja.models.Cobro;
import org.msvc_caja.models.entity.Caja;

import java.util.List;
import java.util.Optional;

public interface CajaService {
    Caja guardar(Caja caja);
    List<Caja> listar();
    Optional<Caja> porId(Long id);
    void eliminar(Long id);


    //Metodos remotos
    List<Caja> cajaListCobros();

    Optional<Cobro> cobrar(Cobro idcobro);

    Optional<Caja> cajaAbierta();

    Double calcularSaldo(Long id);
}
