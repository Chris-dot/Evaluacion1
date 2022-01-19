package cl.nttdata.petclinic.repository;

import cl.nttdata.petclinic.domain.VetSpecialtie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the VetSpecialtie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VetSpecialtieRepository extends JpaRepository<VetSpecialtie, Long> {}
