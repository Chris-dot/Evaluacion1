package cl.nttdata.petclinic.repository;

import cl.nttdata.petclinic.domain.Type;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Type entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {}
