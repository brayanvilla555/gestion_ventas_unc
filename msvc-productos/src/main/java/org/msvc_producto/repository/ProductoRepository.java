package org.msvc_producto.repository;

import org.msvc_producto.entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto,Long > {
}
