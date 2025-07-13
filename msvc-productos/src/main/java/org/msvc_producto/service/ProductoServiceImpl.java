package org.msvc_producto.service;

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

}
