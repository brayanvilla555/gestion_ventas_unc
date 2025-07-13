package org.msvc_caja.repositories;

import org.msvc_caja.models.entity.Caja;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CajaRepository extends CrudRepository<Caja, Long> {
}
