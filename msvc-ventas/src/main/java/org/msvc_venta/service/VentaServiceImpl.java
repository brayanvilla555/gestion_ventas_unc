package org.msvc_venta.service;

import org.msvc_venta.integration.clientes.client.ClienteClient;
import org.msvc_venta.integration.clientes.model.dto.ClienteDto;
import org.msvc_venta.model.entity.ItemVenta;
import org.msvc_venta.model.entity.Venta;
import org.msvc_venta.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl implements VentaService{

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteClient clienteClient;

    @Override
    public List<Venta> listVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta ventaRequent) {
        if(ventaRequent == null) throw new IllegalArgumentException("El cliente no puede ser nulo");
        ventaRequent.setId(null);
        Venta nuevaVenta = Venta.builder()
                .fechaVenta(ventaRequent.getFechaVenta())
                .direccion(ventaRequent.getDireccion())
                .clieteId(this.existeCliente(ventaRequent.getClieteId()).getId())
                .clieteId(ventaRequent.getClieteId())
                .estado(ventaRequent.getEstado())
                .itemsVenta(new ArrayList<>())
                .build();
        if(ventaRequent.getItemsVenta() != null) {
            for (ItemVenta item : ventaRequent.getItemsVenta()){
                item.calcularSubTotal();
                nuevaVenta.agregarItem(item);
            }
        }
        return ventaRepository.save(nuevaVenta);
    }

    private ClienteDto existeCliente(long idCliente){
        return clienteClient.buscarClientePorId(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con el id: " + idCliente + " no existe"));
    }

    @Override
    public Venta editar(Long id, Venta venta) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser nulo");
        if (venta == null) throw new IllegalArgumentException("El cliente no puede ser nulo");

        Venta buscarVenta = ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con el id: " + id + " no existe"));

        buscarVenta.setId(id);
        buscarVenta.setClieteId(venta.getClieteId());
        buscarVenta.setItemsVenta(venta.getItemsVenta());
        buscarVenta.setEstado(venta.getEstado());
        buscarVenta.setTotal(venta.getTotal());
        buscarVenta.setFechaVenta(venta.getFechaVenta());
        buscarVenta.setDireccion(venta.getDireccion());
        return ventaRepository.save(buscarVenta);
    }

    @Override
    public Venta buscarPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser nulo");

        return  ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con el id: " + id + " no existe"));
    }

    @Override
    public boolean eliminar(Long id) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser nulo");

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con el id: " + id + " no existe"));

        if(venta != null) {
            ventaRepository.delete(venta);
            return true;
        }
        return false;
    }
}
