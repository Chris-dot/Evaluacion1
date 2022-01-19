package cl.nttdata.petclinic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.nttdata.petclinic.IntegrationTest;
import cl.nttdata.petclinic.domain.Specialtie;
import cl.nttdata.petclinic.repository.SpecialtieRepository;
import cl.nttdata.petclinic.service.dto.SpecialtieDTO;
import cl.nttdata.petclinic.service.mapper.SpecialtieMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SpecialtieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpecialtieResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/specialties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpecialtieRepository specialtieRepository;

    @Autowired
    private SpecialtieMapper specialtieMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecialtieMockMvc;

    private Specialtie specialtie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialtie createEntity(EntityManager em) {
        Specialtie specialtie = new Specialtie().name(DEFAULT_NAME);
        return specialtie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialtie createUpdatedEntity(EntityManager em) {
        Specialtie specialtie = new Specialtie().name(UPDATED_NAME);
        return specialtie;
    }

    @BeforeEach
    public void initTest() {
        specialtie = createEntity(em);
    }

    @Test
    @Transactional
    void createSpecialtie() throws Exception {
        int databaseSizeBeforeCreate = specialtieRepository.findAll().size();
        // Create the Specialtie
        SpecialtieDTO specialtieDTO = specialtieMapper.toDto(specialtie);
        restSpecialtieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialtieDTO)))
            .andExpect(status().isCreated());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeCreate + 1);
        Specialtie testSpecialtie = specialtieList.get(specialtieList.size() - 1);
        assertThat(testSpecialtie.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createSpecialtieWithExistingId() throws Exception {
        // Create the Specialtie with an existing ID
        specialtie.setId(1L);
        SpecialtieDTO specialtieDTO = specialtieMapper.toDto(specialtie);

        int databaseSizeBeforeCreate = specialtieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialtieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialtieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialtieRepository.findAll().size();
        // set the field null
        specialtie.setName(null);

        // Create the Specialtie, which fails.
        SpecialtieDTO specialtieDTO = specialtieMapper.toDto(specialtie);

        restSpecialtieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialtieDTO)))
            .andExpect(status().isBadRequest());

        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSpecialties() throws Exception {
        // Initialize the database
        specialtieRepository.saveAndFlush(specialtie);

        // Get all the specialtieList
        restSpecialtieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialtie.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getSpecialtie() throws Exception {
        // Initialize the database
        specialtieRepository.saveAndFlush(specialtie);

        // Get the specialtie
        restSpecialtieMockMvc
            .perform(get(ENTITY_API_URL_ID, specialtie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specialtie.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSpecialtie() throws Exception {
        // Get the specialtie
        restSpecialtieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSpecialtie() throws Exception {
        // Initialize the database
        specialtieRepository.saveAndFlush(specialtie);

        int databaseSizeBeforeUpdate = specialtieRepository.findAll().size();

        // Update the specialtie
        Specialtie updatedSpecialtie = specialtieRepository.findById(specialtie.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialtie are not directly saved in db
        em.detach(updatedSpecialtie);
        updatedSpecialtie.name(UPDATED_NAME);
        SpecialtieDTO specialtieDTO = specialtieMapper.toDto(updatedSpecialtie);

        restSpecialtieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specialtieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialtieDTO))
            )
            .andExpect(status().isOk());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeUpdate);
        Specialtie testSpecialtie = specialtieList.get(specialtieList.size() - 1);
        assertThat(testSpecialtie.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = specialtieRepository.findAll().size();
        specialtie.setId(count.incrementAndGet());

        // Create the Specialtie
        SpecialtieDTO specialtieDTO = specialtieMapper.toDto(specialtie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialtieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specialtieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialtieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = specialtieRepository.findAll().size();
        specialtie.setId(count.incrementAndGet());

        // Create the Specialtie
        SpecialtieDTO specialtieDTO = specialtieMapper.toDto(specialtie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialtieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialtieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = specialtieRepository.findAll().size();
        specialtie.setId(count.incrementAndGet());

        // Create the Specialtie
        SpecialtieDTO specialtieDTO = specialtieMapper.toDto(specialtie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialtieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialtieDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpecialtieWithPatch() throws Exception {
        // Initialize the database
        specialtieRepository.saveAndFlush(specialtie);

        int databaseSizeBeforeUpdate = specialtieRepository.findAll().size();

        // Update the specialtie using partial update
        Specialtie partialUpdatedSpecialtie = new Specialtie();
        partialUpdatedSpecialtie.setId(specialtie.getId());

        restSpecialtieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecialtie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecialtie))
            )
            .andExpect(status().isOk());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeUpdate);
        Specialtie testSpecialtie = specialtieList.get(specialtieList.size() - 1);
        assertThat(testSpecialtie.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSpecialtieWithPatch() throws Exception {
        // Initialize the database
        specialtieRepository.saveAndFlush(specialtie);

        int databaseSizeBeforeUpdate = specialtieRepository.findAll().size();

        // Update the specialtie using partial update
        Specialtie partialUpdatedSpecialtie = new Specialtie();
        partialUpdatedSpecialtie.setId(specialtie.getId());

        partialUpdatedSpecialtie.name(UPDATED_NAME);

        restSpecialtieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecialtie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecialtie))
            )
            .andExpect(status().isOk());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeUpdate);
        Specialtie testSpecialtie = specialtieList.get(specialtieList.size() - 1);
        assertThat(testSpecialtie.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = specialtieRepository.findAll().size();
        specialtie.setId(count.incrementAndGet());

        // Create the Specialtie
        SpecialtieDTO specialtieDTO = specialtieMapper.toDto(specialtie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialtieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, specialtieDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialtieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = specialtieRepository.findAll().size();
        specialtie.setId(count.incrementAndGet());

        // Create the Specialtie
        SpecialtieDTO specialtieDTO = specialtieMapper.toDto(specialtie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialtieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialtieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = specialtieRepository.findAll().size();
        specialtie.setId(count.incrementAndGet());

        // Create the Specialtie
        SpecialtieDTO specialtieDTO = specialtieMapper.toDto(specialtie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialtieMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(specialtieDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specialtie in the database
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpecialtie() throws Exception {
        // Initialize the database
        specialtieRepository.saveAndFlush(specialtie);

        int databaseSizeBeforeDelete = specialtieRepository.findAll().size();

        // Delete the specialtie
        restSpecialtieMockMvc
            .perform(delete(ENTITY_API_URL_ID, specialtie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specialtie> specialtieList = specialtieRepository.findAll();
        assertThat(specialtieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
