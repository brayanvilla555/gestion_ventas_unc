package org.msvc_cobro.repositories;

import org.msvc_cobro.models.entity.Cobro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CobroRepository extends CrudRepository<Cobro, Long> {
}
