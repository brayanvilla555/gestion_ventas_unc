package org.msvc_cobro.services;

import org.msvc_cobro.models.VentaDTO;
import org.msvc_cobro.models.entity.Cobro;

import java.util.List;
import java.util.Optional;

public interface CobroService {
    Cobro guardar(Cobro cobro);
    List<Cobro> listar();
    Optional<Cobro> porId(Long id);
    void eliminar(Long id);

    Optional<Cobro> generarCobro(VentaDTO ventaDTO);

    Optional<Cobro> cobrar(Cobro cobro);

    List<Cobro> cobrosPorCobrar();
}
