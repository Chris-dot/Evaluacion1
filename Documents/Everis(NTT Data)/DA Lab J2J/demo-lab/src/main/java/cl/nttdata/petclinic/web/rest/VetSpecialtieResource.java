package cl.nttdata.petclinic.web.rest;

import cl.nttdata.petclinic.repository.VetSpecialtieRepository;
import cl.nttdata.petclinic.service.VetSpecialtieService;
import cl.nttdata.petclinic.service.dto.VetSpecialtieDTO;
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
 * REST controller for managing {@link cl.nttdata.petclinic.domain.VetSpecialtie}.
 */
@RestController
@RequestMapping("/api")
public class VetSpecialtieResource {

    private final Logger log = LoggerFactory.getLogger(VetSpecialtieResource.class);

    private static final String ENTITY_NAME = "vetSpecialtie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VetSpecialtieService vetSpecialtieService;

    private final VetSpecialtieRepository vetSpecialtieRepository;

    public VetSpecialtieResource(VetSpecialtieService vetSpecialtieService, VetSpecialtieRepository vetSpecialtieRepository) {
        this.vetSpecialtieService = vetSpecialtieService;
        this.vetSpecialtieRepository = vetSpecialtieRepository;
    }

    /**
     * {@code POST  /vet-specialties} : Create a new vetSpecialtie.
     *
     * @param vetSpecialtieDTO the vetSpecialtieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vetSpecialtieDTO, or with status {@code 400 (Bad Request)} if the vetSpecialtie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vet-specialties")
    public ResponseEntity<VetSpecialtieDTO> createVetSpecialtie(@Valid @RequestBody VetSpecialtieDTO vetSpecialtieDTO)
        throws URISyntaxException {
        log.debug("REST request to save VetSpecialtie : {}", vetSpecialtieDTO);
        if (vetSpecialtieDTO.getId() != null) {
            throw new BadRequestAlertException("A new vetSpecialtie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VetSpecialtieDTO result = vetSpecialtieService.save(vetSpecialtieDTO);
        return ResponseEntity
            .created(new URI("/api/vet-specialties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vet-specialties/:id} : Updates an existing vetSpecialtie.
     *
     * @param id the id of the vetSpecialtieDTO to save.
     * @param vetSpecialtieDTO the vetSpecialtieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vetSpecialtieDTO,
     * or with status {@code 400 (Bad Request)} if the vetSpecialtieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vetSpecialtieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vet-specialties/{id}")
    public ResponseEntity<VetSpecialtieDTO> updateVetSpecialtie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VetSpecialtieDTO vetSpecialtieDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VetSpecialtie : {}, {}", id, vetSpecialtieDTO);
        if (vetSpecialtieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vetSpecialtieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vetSpecialtieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VetSpecialtieDTO result = vetSpecialtieService.save(vetSpecialtieDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vetSpecialtieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vet-specialties/:id} : Partial updates given fields of an existing vetSpecialtie, field will ignore if it is null
     *
     * @param id the id of the vetSpecialtieDTO to save.
     * @param vetSpecialtieDTO the vetSpecialtieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vetSpecialtieDTO,
     * or with status {@code 400 (Bad Request)} if the vetSpecialtieDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vetSpecialtieDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vetSpecialtieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vet-specialties/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VetSpecialtieDTO> partialUpdateVetSpecialtie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VetSpecialtieDTO vetSpecialtieDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VetSpecialtie partially : {}, {}", id, vetSpecialtieDTO);
        if (vetSpecialtieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vetSpecialtieDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vetSpecialtieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VetSpecialtieDTO> result = vetSpecialtieService.partialUpdate(vetSpecialtieDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vetSpecialtieDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vet-specialties} : get all the vetSpecialties.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vetSpecialties in body.
     */
    @GetMapping("/vet-specialties")
    public ResponseEntity<List<VetSpecialtieDTO>> getAllVetSpecialties(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of VetSpecialties");
        Page<VetSpecialtieDTO> page = vetSpecialtieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vet-specialties/:id} : get the "id" vetSpecialtie.
     *
     * @param id the id of the vetSpecialtieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vetSpecialtieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vet-specialties/{id}")
    public ResponseEntity<VetSpecialtieDTO> getVetSpecialtie(@PathVariable Long id) {
        log.debug("REST request to get VetSpecialtie : {}", id);
        Optional<VetSpecialtieDTO> vetSpecialtieDTO = vetSpecialtieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vetSpecialtieDTO);
    }

    /**
     * {@code DELETE  /vet-specialties/:id} : delete the "id" vetSpecialtie.
     *
     * @param id the id of the vetSpecialtieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vet-specialties/{id}")
    public ResponseEntity<Void> deleteVetSpecialtie(@PathVariable Long id) {
        log.debug("REST request to delete VetSpecialtie : {}", id);
        vetSpecialtieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
