package cl.nttdata.petclinic.repository;

import cl.nttdata.petclinic.domain.Vet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Vet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {}
