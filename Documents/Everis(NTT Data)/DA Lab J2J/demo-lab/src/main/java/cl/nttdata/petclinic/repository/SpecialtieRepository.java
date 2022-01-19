package cl.nttdata.petclinic.repository;

import cl.nttdata.petclinic.domain.Specialtie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Specialtie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialtieRepository extends JpaRepository<Specialtie, Long> {}
