package org.msvc_clientes.service;

import org.msvc_clientes.client.venta.pojo.Venta;
import org.msvc_clientes.model.entity.Cliente;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

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

    //listar ventas del cliente
    Optional<List<Venta>> listarVentasPorCliente(Long idCliente);

}
