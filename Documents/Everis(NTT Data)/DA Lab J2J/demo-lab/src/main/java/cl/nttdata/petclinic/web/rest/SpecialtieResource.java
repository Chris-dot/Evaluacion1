package cl.nttdata.petclinic.web.rest;

import cl.nttdata.petclinic.repository.SpecialtieRepository;
import cl.nttdata.petclinic.service.SpecialtieService;
import cl.nttdata.petclinic.service.dto.SpecialtieDTO;
import cl.nttdata.petclinic.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cl.nttdata.petclinic.domain.Specialtie}.
 */
@RestController
@RequestMapping("/api")
public class SpecialtieResource {

    private final Logger log = LoggerFactory.getLogger(SpecialtieResource.class);

    private static final String ENTITY_NAME = "specialtie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialtieService specialtieService;

    private final SpecialtieRepository specialtieRepository;

    public SpecialtieResource(SpecialtieService specialtieService, SpecialtieRepository specialtieRepository) {
        this.specialtieService = specialtieService;
        this.specialtieRepository = specialtieRepository;
    }

    /**
     * {@code POST  /specialties} : Create a new specialtie.
     *
     * @param specialtieDTO the specialtieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialtieDTO, or with status {@code 400 (Bad Request)} if the specialtie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specialties")
    public ResponseEntity<SpecialtieDTO> createSpecialtie(@Valid @RequestBody SpecialtieDTO specialtieDTO) throws URISyntaxException {
        log.debug("REST request to save Specialtie : {}", specialtieDTO);
        if (specialtieDTO.getId() != null) {
            throw new BadRequestAlertException("A new specialtie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialtieDTO result = specialtieService.save(specialtieDTO);
        return ResponseEntity
            .created(new URI("/api/specialties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specialties/:id} : Updates an existing specialtie.
     *
     * @param id the id of the specialtieDTO to save.
     * @param specialtieDTO the specialtieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialtieDTO,
     * or with status {@code 400 (Bad Request)} if the specialtieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialtieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specialties/{id}")
    public ResponseEntity<SpecialtieDTO> updateSpecialtie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SpecialtieDTO specialtieDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Specialtie : {}, {}", id, specialtieDTO);
        if (specialtieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialtieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialtieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpecialtieDTO result = specialtieService.save(specialtieDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialtieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /specialties/:id} : Partial updates given fields of an existing specialtie, field will ignore if it is null
     *
     * @param id the id of the specialtieDTO to save.
     * @param specialtieDTO the specialtieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialtieDTO,
     * or with status {@code 400 (Bad Request)} if the specialtieDTO is not valid,
     * or with status {@code 404 (Not Found)} if the specialtieDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the specialtieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/specialties/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpecialtieDTO> partialUpdateSpecialtie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SpecialtieDTO specialtieDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Specialtie partially : {}, {}", id, specialtieDTO);
        if (specialtieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialtieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialtieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpecialtieDTO> result = specialtieService.partialUpdate(specialtieDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialtieDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /specialties} : get all the specialties.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialties in body.
     */
    @GetMapping("/specialties")
    public ResponseEntity<List<SpecialtieDTO>> getAllSpecialties(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Specialties");
        Page<SpecialtieDTO> page = specialtieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specialties/:id} : get the "id" specialtie.
     *
     * @param id the id of the specialtieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialtieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specialties/{id}")
    public ResponseEntity<SpecialtieDTO> getSpecialtie(@PathVariable Long id) {
        log.debug("REST request to get Specialtie : {}", id);
        Optional<SpecialtieDTO> specialtieDTO = specialtieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialtieDTO);
    }

    /**
     * {@code DELETE  /specialties/:id} : delete the "id" specialtie.
     *
     * @param id the id of the specialtieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specialties/{id}")
    public ResponseEntity<Void> deleteSpecialtie(@PathVariable Long id) {
        log.debug("REST request to delete Specialtie : {}", id);
        specialtieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
