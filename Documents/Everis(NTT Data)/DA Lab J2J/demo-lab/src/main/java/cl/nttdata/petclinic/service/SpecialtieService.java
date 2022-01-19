package cl.nttdata.petclinic.service;

import cl.nttdata.petclinic.service.dto.SpecialtieDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link cl.nttdata.petclinic.domain.Specialtie}.
 */
public interface SpecialtieService {
    /**
     * Save a specialtie.
     *
     * @param specialtieDTO the entity to save.
     * @return the persisted entity.
     */
    SpecialtieDTO save(SpecialtieDTO specialtieDTO);

    /**
     * Partially updates a specialtie.
     *
     * @param specialtieDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SpecialtieDTO> partialUpdate(SpecialtieDTO specialtieDTO);

    /**
     * Get all the specialties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SpecialtieDTO> findAll(Pageable pageable);

    /**
     * Get the "id" specialtie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpecialtieDTO> findOne(Long id);

    /**
     * Delete the "id" specialtie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
