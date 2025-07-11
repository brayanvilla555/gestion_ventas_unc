package org.msvc_clientes.service;

import org.msvc_clientes.model.entity.Cliente;

import java.util.List;

public interface ClienteService {

    //listar
    List<Cliente> clientes();

    //guardar
    Cliente guardar(Cliente cliente);

    //editar
    Cliente editar(Long id, Cliente cliente);

    //buscar por id
    Cliente buscarPorId(Long id);

    //eliminar
    Boolean eliminar(Long id);

}
