package org.msvc_venta.integration.cobros.service;

import org.msvc_venta.integration.cobros.client.CobroClient;
import org.msvc_venta.integration.cobros.model.dto.CobroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CobroServiceImpl implements CobroService  {
    @Autowired
    private CobroClient cobroClient;

    //Método remoto para crear
    public Optional<CobroDto> registrarCobro(CobroDto cobroDto){
        if(cobroDto == null) throw new IllegalArgumentException("El cobro no puede ser nulo");
        return cobroClient.registrarCobro(cobroDto);
    }
}
