package org.msvc_clientes.service;

import jakarta.persistence.EntityNotFoundException;
import org.msvc_clientes.client.venta.VentaClient;
import org.msvc_clientes.client.venta.pojo.Venta;
import org.msvc_clientes.model.entity.Cliente;
import org.msvc_clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private VentaClient ventaClient;

    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    public List<Cliente> clientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        if(cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo");
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente editar(Long id, Cliente cliente) {
        if(id == null) throw new IllegalArgumentException("El id no puede ser nulo");
        if(cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo");

        Cliente buscarCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con el id: " + id + " no existe"));

        buscarCliente.setNombre(cliente.getNombre());
        buscarCliente.setApellido(cliente.getApellido());
        buscarCliente.setCorreo(cliente.getCorreo());
        buscarCliente.setTelefono(cliente.getTelefono());
        buscarCliente.setNumeroIdentificacion(cliente.getNumeroIdentificacion());
        buscarCliente.setEstado(cliente.getEstado());

        return clienteRepository.save(buscarCliente);
    }

    @Override
    public Cliente buscarPorId(Long id) {
        if(id == null) throw new IllegalArgumentException("El id no puede ser nulo");
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()) {
            return cliente.get();
        }else{
            throw new IllegalArgumentException("El cliente con el id: " + id + " no existe");
        }
    }

    @Override
    public Boolean eliminar(Long id) {
        if(id == null) throw new IllegalArgumentException("El id no puede ser nulo");
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con el id: " + id + " no existe"));
        if(cliente != null) {
            clienteRepository.delete(cliente);
            return true;
        }
        return false;
    }

    //metodo remoto
    @Override
    public Optional<List<Venta>> listarVentasPorCliente(Long idCliente){
        Optional<List<Venta>> ventas = ventaClient.listarVentasPorCliente(idCliente);
        if (ventas.isPresent()){
            return ventas;
        }else{
            throw new IllegalArgumentException("El cliente con el id: " + idCliente + " no existe");
        }
    }
}
