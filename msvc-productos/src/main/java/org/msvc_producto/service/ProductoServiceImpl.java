package org.msvc_producto.service;

import org.msvc_producto.entity.EstadoProducto;
import org.msvc_producto.entity.Producto;
import org.msvc_producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Override
   @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return (List<Producto>)repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }


    @Override
    @Transactional
    public void eliminar(Long id) {
            repository.deleteById(id);
    }

    @Override
    public Optional<Producto> aumentarStockAlmacen(Long id, int cantidad) {
        Optional<Producto> productoEncontrado=repository.findById(id);
        if(productoEncontrado.isPresent()) {
            Producto producto=productoEncontrado.get();
            producto.aumentarStockAlmacen(cantidad);
            return Optional.of(repository.save(producto));
        }
        return Optional.empty();  // Producto no encontrado o cantidad inválida
    }

    @Override
    public Optional<Producto> disminuirStockTienda(Long id, int cantidad) {
        Optional<Producto> productoEncontrado = repository.findById(id);
        if (productoEncontrado.isPresent()) {
            Producto producto = productoEncontrado.get();
            if (producto.getStockTienda() >= cantidad && cantidad > 0) {
                producto.disminuirStockTienda(cantidad);
                return Optional.of(repository.save(producto));
            }
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Producto> transferirStockTienda(Long id, int cantidad) {
        Optional<Producto> productoEncontrado = repository.findById(id);

        if (productoEncontrado.isPresent()) {
            Producto producto = productoEncontrado.get();
            producto.transferirStock(cantidad);
            return Optional.of(repository.save(producto));
        }
        return Optional.empty();  // Producto no encontrado
    }

    @Override
    @Transactional
    public Optional<Producto> actualizarEstado(Long id, EstadoProducto nuevoEstado) {
        Optional<Producto> productoEncontrado = repository.findById(id);

        if (productoEncontrado.isPresent()) {
            Producto producto = productoEncontrado.get();
            producto.actualizarEstado(nuevoEstado);
            return Optional.of(repository.save(producto));
        }

        return Optional.empty(); // Producto no encontrado
    }

    @Override
    @Transactional
    public Optional<Producto> actualizarPrecio(Long id, double nuevoPrecio) {
        Optional<Producto> productoEncontrado = repository.findById(id);
        if (productoEncontrado.isPresent() ) {
            Producto producto = productoEncontrado.get();
            producto.actualizarPrecio(nuevoPrecio);
            return Optional.of(repository.save(producto));
        }
        return Optional.empty();
    }

}
