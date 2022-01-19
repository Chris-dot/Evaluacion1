package cl.nttdata.petclinic.service;

import cl.nttdata.petclinic.service.dto.VetSpecialtieDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link cl.nttdata.petclinic.domain.VetSpecialtie}.
 */
public interface VetSpecialtieService {
    /**
     * Save a vetSpecialtie.
     *
     * @param vetSpecialtieDTO the entity to save.
     * @return the persisted entity.
     */
    VetSpecialtieDTO save(VetSpecialtieDTO vetSpecialtieDTO);

    /**
     * Partially updates a vetSpecialtie.
     *
     * @param vetSpecialtieDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VetSpecialtieDTO> partialUpdate(VetSpecialtieDTO vetSpecialtieDTO);

    /**
     * Get all the vetSpecialties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VetSpecialtieDTO> findAll(Pageable pageable);

    /**
     * Get the "id" vetSpecialtie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VetSpecialtieDTO> findOne(Long id);

    /**
     * Delete the "id" vetSpecialtie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
