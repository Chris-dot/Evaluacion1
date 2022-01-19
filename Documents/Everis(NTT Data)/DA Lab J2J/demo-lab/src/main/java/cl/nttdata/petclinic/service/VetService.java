package cl.nttdata.petclinic.service;

import cl.nttdata.petclinic.service.dto.VetDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link cl.nttdata.petclinic.domain.Vet}.
 */
public interface VetService {
    /**
     * Save a vet.
     *
     * @param vetDTO the entity to save.
     * @return the persisted entity.
     */
    VetDTO save(VetDTO vetDTO);

    /**
     * Partially updates a vet.
     *
     * @param vetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VetDTO> partialUpdate(VetDTO vetDTO);

    /**
     * Get all the vets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" vet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VetDTO> findOne(Long id);

    /**
     * Delete the "id" vet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
