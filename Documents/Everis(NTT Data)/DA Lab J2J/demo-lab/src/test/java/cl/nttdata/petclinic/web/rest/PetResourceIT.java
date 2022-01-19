package cl.nttdata.petclinic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.nttdata.petclinic.IntegrationTest;
import cl.nttdata.petclinic.domain.Owner;
import cl.nttdata.petclinic.domain.Pet;
import cl.nttdata.petclinic.domain.Type;
import cl.nttdata.petclinic.repository.PetRepository;
import cl.nttdata.petclinic.service.dto.PetDTO;
import cl.nttdata.petclinic.service.mapper.PetMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link PetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/pets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPetMockMvc;

    private Pet pet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pet createEntity(EntityManager em) {
        Pet pet = new Pet().name(DEFAULT_NAME).birthDate(DEFAULT_BIRTH_DATE);
        // Add required entity
        Owner owner;
        if (TestUtil.findAll(em, Owner.class).isEmpty()) {
            owner = OwnerResourceIT.createEntity(em);
            em.persist(owner);
            em.flush();
        } else {
            owner = TestUtil.findAll(em, Owner.class).get(0);
        }
        pet.setOwner(owner);
        // Add required entity
        Type type;
        if (TestUtil.findAll(em, Type.class).isEmpty()) {
            type = TypeResourceIT.createEntity(em);
            em.persist(type);
            em.flush();
        } else {
            type = TestUtil.findAll(em, Type.class).get(0);
        }
        pet.setType(type);
        return pet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pet createUpdatedEntity(EntityManager em) {
        Pet pet = new Pet().name(UPDATED_NAME).birthDate(UPDATED_BIRTH_DATE);
        // Add required entity
        Owner owner;
        if (TestUtil.findAll(em, Owner.class).isEmpty()) {
            owner = OwnerResourceIT.createUpdatedEntity(em);
            em.persist(owner);
            em.flush();
        } else {
            owner = TestUtil.findAll(em, Owner.class).get(0);
        }
        pet.setOwner(owner);
        // Add required entity
        Type type;
        if (TestUtil.findAll(em, Type.class).isEmpty()) {
            type = TypeResourceIT.createUpdatedEntity(em);
            em.persist(type);
            em.flush();
        } else {
            type = TestUtil.findAll(em, Type.class).get(0);
        }
        pet.setType(type);
        return pet;
    }

    @BeforeEach
    public void initTest() {
        pet = createEntity(em);
    }

    @Test
    @Transactional
    void createPet() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();
        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);
        restPetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isCreated());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate + 1);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPet.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
    }

    @Test
    @Transactional
    void createPetWithExistingId() throws Exception {
        // Create the Pet with an existing ID
        pet.setId(1L);
        PetDTO petDTO = petMapper.toDto(pet);

        int databaseSizeBeforeCreate = petRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setName(null);

        // Create the Pet, which fails.
        PetDTO petDTO = petMapper.toDto(pet);

        restPetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isBadRequest());

        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setBirthDate(null);

        // Create the Pet, which fails.
        PetDTO petDTO = petMapper.toDto(pet);

        restPetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isBadRequest());

        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPets() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList
        restPetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())));
    }

    @Test
    @Transactional
    void getPet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get the pet
        restPetMockMvc
            .perform(get(ENTITY_API_URL_ID, pet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPet() throws Exception {
        // Get the pet
        restPetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Update the pet
        Pet updatedPet = petRepository.findById(pet.getId()).get();
        // Disconnect from session so that the updates on updatedPet are not directly saved in db
        em.detach(updatedPet);
        updatedPet.name(UPDATED_NAME).birthDate(UPDATED_BIRTH_DATE);
        PetDTO petDTO = petMapper.toDto(updatedPet);

        restPetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, petDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPet.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPet() throws Exception {
        int databaseSizeBeforeUpdate = petRepository.findAll().size();
        pet.setId(count.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, petDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPet() throws Exception {
        int databaseSizeBeforeUpdate = petRepository.findAll().size();
        pet.setId(count.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(petDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPet() throws Exception {
        int databaseSizeBeforeUpdate = petRepository.findAll().size();
        pet.setId(count.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePetWithPatch() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Update the pet using partial update
        Pet partialUpdatedPet = new Pet();
        partialUpdatedPet.setId(pet.getId());

        restPetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPet))
            )
            .andExpect(status().isOk());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPet.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePetWithPatch() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Update the pet using partial update
        Pet partialUpdatedPet = new Pet();
        partialUpdatedPet.setId(pet.getId());

        partialUpdatedPet.name(UPDATED_NAME).birthDate(UPDATED_BIRTH_DATE);

        restPetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPet))
            )
            .andExpect(status().isOk());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPet.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPet() throws Exception {
        int databaseSizeBeforeUpdate = petRepository.findAll().size();
        pet.setId(count.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, petDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(petDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPet() throws Exception {
        int databaseSizeBeforeUpdate = petRepository.findAll().size();
        pet.setId(count.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(petDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPet() throws Exception {
        int databaseSizeBeforeUpdate = petRepository.findAll().size();
        pet.setId(count.incrementAndGet());

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPetMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        int databaseSizeBeforeDelete = petRepository.findAll().size();

        // Delete the pet
        restPetMockMvc.perform(delete(ENTITY_API_URL_ID, pet.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
