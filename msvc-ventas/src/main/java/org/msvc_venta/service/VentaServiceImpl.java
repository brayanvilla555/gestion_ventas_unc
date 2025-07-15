package org.msvc_venta.service;

import org.msvc_venta.integration.clientes.client.ClienteClient;
import org.msvc_venta.integration.clientes.model.dto.ClienteDto;
import org.msvc_venta.integration.clientes.service.ClienteService;
import org.msvc_venta.integration.cobros.model.dto.CobroDto;
import org.msvc_venta.integration.cobros.service.CobroService;
import org.msvc_venta.integration.productos.client.ProductoClient;
import org.msvc_venta.integration.productos.model.dto.ProductoDto;
import org.msvc_venta.integration.productos.service.ProductoService;
import org.msvc_venta.model.entity.ItemVenta;
import org.msvc_venta.model.entity.Venta;
import org.msvc_venta.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VentaServiceImpl implements VentaService{

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CobroService cobroService;

    @Override
    public List<Venta> listVentas() {
        List<Venta> vetas = ventaRepository.findAll();
        vetas.forEach(venta -> {
            ClienteDto cliente = this.clienteService.existeCliente(venta.getClieteId());
            if(cliente.getId() != null) {
                venta.setCliente(cliente);
            }else{
                venta.setCliente(null);
            }
        });
        return vetas;
    }

    @Override
    public Venta guardar(Venta ventaRequent) {
        if(ventaRequent == null) throw new IllegalArgumentException("El cliente no puede ser nulo");
        ventaRequent.setId(null);
        Venta nuevaVenta = Venta.builder()
                .fechaVenta(ventaRequent.getFechaVenta())
                .direccion(ventaRequent.getDireccion())
                .clieteId(
                        this.clienteService.existeCliente(ventaRequent.getClieteId()).getId()
                )
                .clieteId(ventaRequent.getClieteId())
                .estado(ventaRequent.getEstado())
                .itemsVenta(new ArrayList<>())
                .build();
        //agregamos los items a la venta
        if(ventaRequent.getItemsVenta() != null) {
            for (ItemVenta item : ventaRequent.getItemsVenta()){
                //consultamos para ver si ese producto existe
                ProductoDto existeProducto = this.productoService.existeProducto(item.getProductoId());
                if(existeProducto == null) {
                    throw new IllegalArgumentException("El producto con el id: " + item.getProductoId() + " no existe");
                }
                //verificamos si existe la cantidad en la tienda
                boolean cantidadSuficiente = existeProducto.getStockTienda() >= item.getCantidad();
                boolean precioCorrecto = Objects.equals(existeProducto.getPrecio(), item.getPrecio());
                if(cantidadSuficiente && precioCorrecto){
                    //calculamos sus montos
                    item.calcularSubTotal();
                    nuevaVenta.agregarItem(item);
                    //actualizamos el stock en la tienda
                    this.actualizarStockTienda(item.getProductoId(), item.getCantidad());
                } else{
                    throw new IllegalArgumentException("No hay suficiente stock o precio es incorrecto del producto con id: " + item.getProductoId());
                }
            }
        }

        Venta venta = ventaRepository.save(nuevaVenta);
        //registramos el pago si la venta
        CobroDto nuevoCobro = CobroDto.builder()
                .ventaId(venta.getId())
                .montoTotal(venta.getTotal())
                .observaciones("Procesando el pago")
                .build();
        cobroService.registrarCobro(nuevoCobro);

         return venta;
    }

    private void actualizarStockTienda(Long idProducto, int cantidad){
        productoService.actualizarStockTienda(idProducto, cantidad);
    }

    @Override
    public Venta editar(Long id, Venta venta) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser nulo");
        if (venta == null) throw new IllegalArgumentException("El cliente no puede ser nulo");

        Venta buscarVenta = ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con el id: " + id + " no existe"));

        ClienteDto clienteDto = this.clienteService.existeCliente(venta.getClieteId());
        if(clienteDto.getId() != null) {
            buscarVenta.setClieteId(clienteDto.getId());
            buscarVenta.setCliente(clienteDto);
        }

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

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El cliente con el id: " + id + " no existe"));

        ClienteDto clienteDto = this.clienteService.existeCliente(venta.getClieteId());
        venta.setCliente(clienteDto);

        return venta;
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
