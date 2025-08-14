package org.msvc_venta.service;

import org.msvc_venta.integration.clientes.model.dto.ClienteDto;
import org.msvc_venta.integration.clientes.service.ClienteService;
import org.msvc_venta.integration.cobros.model.dto.CobroDto;
import org.msvc_venta.integration.cobros.service.CobroService;
import org.msvc_venta.integration.productos.model.dto.ProductoDto;
import org.msvc_venta.integration.productos.service.ProductoService;
import org.msvc_venta.model.entity.ItemVenta;
import org.msvc_venta.model.entity.Venta;
import org.msvc_venta.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Venta guardar(Venta ventaRequest) {
        validarVentaNoNula(ventaRequest);
        procesarClienteEnVenta(ventaRequest);

        Venta nuevaVenta = construirVentaBase(ventaRequest);
        procesarItemsVenta(ventaRequest.getItemsVenta(), nuevaVenta);

        Venta ventaGuardada = ventaRepository.save(nuevaVenta);

        registrarCobro(ventaGuardada);

        return ventaGuardada;
    }

    @Override
    public Venta editar(Long id, Venta ventaRequest) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser nulo");
        validarVentaNoNula(ventaRequest);

        Venta ventaExistente = ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La venta con id: " + id + " no existe"));

        ClienteDto clienteDto = clienteService.existeCliente(ventaRequest.getClieteId());
        ventaExistente.setClieteId(clienteDto.getId());
        ventaExistente.setCliente(clienteDto);

        ventaExistente.getItemsVenta().clear(); // elimina los anteriores (Hibernate lo manejará bien con orphanRemoval=true)
        if (ventaRequest.getItemsVenta() != null) {
            for (ItemVenta nuevoItem : ventaRequest.getItemsVenta()) {
                nuevoItem.setVenta(ventaExistente); //mantener la relación bidireccional
                nuevoItem.calcularSubTotal();
                ventaExistente.getItemsVenta().add(nuevoItem);
            }
        }

        ventaExistente.setEstado(ventaRequest.getEstado());
        ventaExistente.setFechaVenta(ventaRequest.getFechaVenta());
        ventaExistente.setDireccion(ventaRequest.getDireccion());

        return ventaRepository.save(ventaExistente);
    }

    private void actualizarStockTienda(Long idProducto, int cantidad){
        productoService.actualizarStockTienda(idProducto, cantidad);
    }

    private void validarVentaNoNula(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser nula");
        }
    }

    private void procesarClienteEnVenta(Venta ventaRequest) {
        if (ventaRequest.getClieteId() == null && ventaRequest.getCliente() != null) {
            ventaRequest.getCliente().setId(null);
            ClienteDto clienteDto = clienteService.guardarCliente(ventaRequest.getCliente());
            if (clienteDto.getId() == null) {
                throw new IllegalArgumentException("Error al registrar el cliente");
            }
            ventaRequest.setClieteId(clienteDto.getId());
        }
    }

    private Venta construirVentaBase(Venta ventaRequest) {
        return Venta.builder()
                .fechaVenta(ventaRequest.getFechaVenta())
                .direccion(ventaRequest.getDireccion())
                .clieteId(clienteService.existeCliente(ventaRequest.getClieteId()).getId())
                .estado(ventaRequest.getEstado())
                .itemsVenta(new ArrayList<>())
                .build();
    }

    private void procesarItemsVenta(List<ItemVenta> items, Venta venta) {
        if (items == null) return;

        for (ItemVenta item : items) {
            ProductoDto producto = productoService.existeProducto(item.getProductoId());
            if (producto == null) {
                throw new IllegalArgumentException("Producto no encontrado: " + item.getProductoId());
            }

            boolean stockSuficiente = producto.getStockTienda() >= item.getCantidad();
            boolean precioCorrecto = Objects.equals(producto.getPrecio(), item.getPrecio());

            if (!stockSuficiente || !precioCorrecto) {
                throw new IllegalArgumentException("Stock insuficiente o precio incorrecto para producto ID: " + item.getProductoId());
            }

            item.calcularSubTotal();
            venta.agregarItem(item);
            actualizarStockTienda(item.getProductoId(), item.getCantidad());
        }
    }

    private void registrarCobro(Venta venta) {
        CobroDto cobro = CobroDto.builder()
                .ventaId(venta.getId())
                .montoTotal(venta.getTotal())
                .observaciones("Procesando el pago")
                .build();
        cobroService.registrarCobro(cobro);
    }


    @Override
    public Venta buscarPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("El id no puede ser nulo");

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La venta con el id: " + id + " no existe"));

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

    public List<Venta> listarVentasPorCliente(Long idCliente){
        return ventaRepository.findByClieteId(idCliente);
    }
}
