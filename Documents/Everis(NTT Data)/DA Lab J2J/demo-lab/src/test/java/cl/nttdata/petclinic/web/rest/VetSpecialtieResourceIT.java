package cl.nttdata.petclinic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.nttdata.petclinic.IntegrationTest;
import cl.nttdata.petclinic.domain.Specialtie;
import cl.nttdata.petclinic.domain.Vet;
import cl.nttdata.petclinic.domain.VetSpecialtie;
import cl.nttdata.petclinic.repository.VetSpecialtieRepository;
import cl.nttdata.petclinic.service.dto.VetSpecialtieDTO;
import cl.nttdata.petclinic.service.mapper.VetSpecialtieMapper;
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
 * Integration tests for the {@link VetSpecialtieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VetSpecialtieResourceIT {

    private static final String ENTITY_API_URL = "/api/vet-specialties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VetSpecialtieRepository vetSpecialtieRepository;

    @Autowired
    private VetSpecialtieMapper vetSpecialtieMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVetSpecialtieMockMvc;

    private VetSpecialtie vetSpecialtie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VetSpecialtie createEntity(EntityManager em) {
        VetSpecialtie vetSpecialtie = new VetSpecialtie();
        // Add required entity
        Vet vet;
        if (TestUtil.findAll(em, Vet.class).isEmpty()) {
            vet = VetResourceIT.createEntity(em);
            em.persist(vet);
            em.flush();
        } else {
            vet = TestUtil.findAll(em, Vet.class).get(0);
        }
        vetSpecialtie.setVet(vet);
        // Add required entity
        Specialtie specialtie;
        if (TestUtil.findAll(em, Specialtie.class).isEmpty()) {
            specialtie = SpecialtieResourceIT.createEntity(em);
            em.persist(specialtie);
            em.flush();
        } else {
            specialtie = TestUtil.findAll(em, Specialtie.class).get(0);
        }
        vetSpecialtie.setSpecialtie(specialtie);
        return vetSpecialtie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VetSpecialtie createUpdatedEntity(EntityManager em) {
        VetSpecialtie vetSpecialtie = new VetSpecialtie();
        // Add required entity
        Vet vet;
        if (TestUtil.findAll(em, Vet.class).isEmpty()) {
            vet = VetResourceIT.createUpdatedEntity(em);
            em.persist(vet);
            em.flush();
        } else {
            vet = TestUtil.findAll(em, Vet.class).get(0);
        }
        vetSpecialtie.setVet(vet);
        // Add required entity
        Specialtie specialtie;
        if (TestUtil.findAll(em, Specialtie.class).isEmpty()) {
            specialtie = SpecialtieResourceIT.createUpdatedEntity(em);
            em.persist(specialtie);
            em.flush();
        } else {
            specialtie = TestUtil.findAll(em, Specialtie.class).get(0);
        }
        vetSpecialtie.setSpecialtie(specialtie);
        return vetSpecialtie;
    }

    @BeforeEach
    public void initTest() {
        vetSpecialtie = createEntity(em);
    }

    @Test
    @Transactional
    void createVetSpecialtie() throws Exception {
        int databaseSizeBeforeCreate = vetSpecialtieRepository.findAll().size();
        // Create the VetSpecialtie
        VetSpecialtieDTO vetSpecialtieDTO = vetSpecialtieMapper.toDto(vetSpecialtie);
        restVetSpecialtieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vetSpecialtieDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeCreate + 1);
        VetSpecialtie testVetSpecialtie = vetSpecialtieList.get(vetSpecialtieList.size() - 1);
    }

    @Test
    @Transactional
    void createVetSpecialtieWithExistingId() throws Exception {
        // Create the VetSpecialtie with an existing ID
        vetSpecialtie.setId(1L);
        VetSpecialtieDTO vetSpecialtieDTO = vetSpecialtieMapper.toDto(vetSpecialtie);

        int databaseSizeBeforeCreate = vetSpecialtieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVetSpecialtieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vetSpecialtieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVetSpecialties() throws Exception {
        // Initialize the database
        vetSpecialtieRepository.saveAndFlush(vetSpecialtie);

        // Get all the vetSpecialtieList
        restVetSpecialtieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vetSpecialtie.getId().intValue())));
    }

    @Test
    @Transactional
    void getVetSpecialtie() throws Exception {
        // Initialize the database
        vetSpecialtieRepository.saveAndFlush(vetSpecialtie);

        // Get the vetSpecialtie
        restVetSpecialtieMockMvc
            .perform(get(ENTITY_API_URL_ID, vetSpecialtie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vetSpecialtie.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVetSpecialtie() throws Exception {
        // Get the vetSpecialtie
        restVetSpecialtieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVetSpecialtie() throws Exception {
        // Initialize the database
        vetSpecialtieRepository.saveAndFlush(vetSpecialtie);

        int databaseSizeBeforeUpdate = vetSpecialtieRepository.findAll().size();

        // Update the vetSpecialtie
        VetSpecialtie updatedVetSpecialtie = vetSpecialtieRepository.findById(vetSpecialtie.getId()).get();
        // Disconnect from session so that the updates on updatedVetSpecialtie are not directly saved in db
        em.detach(updatedVetSpecialtie);
        VetSpecialtieDTO vetSpecialtieDTO = vetSpecialtieMapper.toDto(updatedVetSpecialtie);

        restVetSpecialtieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vetSpecialtieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vetSpecialtieDTO))
            )
            .andExpect(status().isOk());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeUpdate);
        VetSpecialtie testVetSpecialtie = vetSpecialtieList.get(vetSpecialtieList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingVetSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = vetSpecialtieRepository.findAll().size();
        vetSpecialtie.setId(count.incrementAndGet());

        // Create the VetSpecialtie
        VetSpecialtieDTO vetSpecialtieDTO = vetSpecialtieMapper.toDto(vetSpecialtie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVetSpecialtieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vetSpecialtieDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vetSpecialtieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVetSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = vetSpecialtieRepository.findAll().size();
        vetSpecialtie.setId(count.incrementAndGet());

        // Create the VetSpecialtie
        VetSpecialtieDTO vetSpecialtieDTO = vetSpecialtieMapper.toDto(vetSpecialtie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVetSpecialtieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vetSpecialtieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVetSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = vetSpecialtieRepository.findAll().size();
        vetSpecialtie.setId(count.incrementAndGet());

        // Create the VetSpecialtie
        VetSpecialtieDTO vetSpecialtieDTO = vetSpecialtieMapper.toDto(vetSpecialtie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVetSpecialtieMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vetSpecialtieDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVetSpecialtieWithPatch() throws Exception {
        // Initialize the database
        vetSpecialtieRepository.saveAndFlush(vetSpecialtie);

        int databaseSizeBeforeUpdate = vetSpecialtieRepository.findAll().size();

        // Update the vetSpecialtie using partial update
        VetSpecialtie partialUpdatedVetSpecialtie = new VetSpecialtie();
        partialUpdatedVetSpecialtie.setId(vetSpecialtie.getId());

        restVetSpecialtieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVetSpecialtie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVetSpecialtie))
            )
            .andExpect(status().isOk());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeUpdate);
        VetSpecialtie testVetSpecialtie = vetSpecialtieList.get(vetSpecialtieList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateVetSpecialtieWithPatch() throws Exception {
        // Initialize the database
        vetSpecialtieRepository.saveAndFlush(vetSpecialtie);

        int databaseSizeBeforeUpdate = vetSpecialtieRepository.findAll().size();

        // Update the vetSpecialtie using partial update
        VetSpecialtie partialUpdatedVetSpecialtie = new VetSpecialtie();
        partialUpdatedVetSpecialtie.setId(vetSpecialtie.getId());

        restVetSpecialtieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVetSpecialtie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVetSpecialtie))
            )
            .andExpect(status().isOk());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeUpdate);
        VetSpecialtie testVetSpecialtie = vetSpecialtieList.get(vetSpecialtieList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingVetSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = vetSpecialtieRepository.findAll().size();
        vetSpecialtie.setId(count.incrementAndGet());

        // Create the VetSpecialtie
        VetSpecialtieDTO vetSpecialtieDTO = vetSpecialtieMapper.toDto(vetSpecialtie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVetSpecialtieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vetSpecialtieDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vetSpecialtieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVetSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = vetSpecialtieRepository.findAll().size();
        vetSpecialtie.setId(count.incrementAndGet());

        // Create the VetSpecialtie
        VetSpecialtieDTO vetSpecialtieDTO = vetSpecialtieMapper.toDto(vetSpecialtie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVetSpecialtieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vetSpecialtieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVetSpecialtie() throws Exception {
        int databaseSizeBeforeUpdate = vetSpecialtieRepository.findAll().size();
        vetSpecialtie.setId(count.incrementAndGet());

        // Create the VetSpecialtie
        VetSpecialtieDTO vetSpecialtieDTO = vetSpecialtieMapper.toDto(vetSpecialtie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVetSpecialtieMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vetSpecialtieDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VetSpecialtie in the database
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVetSpecialtie() throws Exception {
        // Initialize the database
        vetSpecialtieRepository.saveAndFlush(vetSpecialtie);

        int databaseSizeBeforeDelete = vetSpecialtieRepository.findAll().size();

        // Delete the vetSpecialtie
        restVetSpecialtieMockMvc
            .perform(delete(ENTITY_API_URL_ID, vetSpecialtie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VetSpecialtie> vetSpecialtieList = vetSpecialtieRepository.findAll();
        assertThat(vetSpecialtieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
