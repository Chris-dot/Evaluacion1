package cl.nttdata.petclinic.service;

import cl.nttdata.petclinic.service.dto.VisitDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link cl.nttdata.petclinic.domain.Visit}.
 */
public interface VisitService {
    /**
     * Save a visit.
     *
     * @param visitDTO the entity to save.
     * @return the persisted entity.
     */
    VisitDTO save(VisitDTO visitDTO);

    /**
     * Partially updates a visit.
     *
     * @param visitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VisitDTO> partialUpdate(VisitDTO visitDTO);

    /**
     * Get all the visits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VisitDTO> findAll(Pageable pageable);

    /**
     * Get the "id" visit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VisitDTO> findOne(Long id);

    /**
     * Delete the "id" visit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
