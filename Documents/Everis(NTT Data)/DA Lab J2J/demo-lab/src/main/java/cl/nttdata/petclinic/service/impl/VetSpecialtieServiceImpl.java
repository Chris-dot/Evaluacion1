package cl.nttdata.petclinic.service.impl;

import cl.nttdata.petclinic.domain.VetSpecialtie;
import cl.nttdata.petclinic.repository.VetSpecialtieRepository;
import cl.nttdata.petclinic.service.VetSpecialtieService;
import cl.nttdata.petclinic.service.dto.VetSpecialtieDTO;
import cl.nttdata.petclinic.service.mapper.VetSpecialtieMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VetSpecialtie}.
 */
@Service
@Transactional
public class VetSpecialtieServiceImpl implements VetSpecialtieService {

    private final Logger log = LoggerFactory.getLogger(VetSpecialtieServiceImpl.class);

    private final VetSpecialtieRepository vetSpecialtieRepository;

    private final VetSpecialtieMapper vetSpecialtieMapper;

    public VetSpecialtieServiceImpl(VetSpecialtieRepository vetSpecialtieRepository, VetSpecialtieMapper vetSpecialtieMapper) {
        this.vetSpecialtieRepository = vetSpecialtieRepository;
        this.vetSpecialtieMapper = vetSpecialtieMapper;
    }

    @Override
    public VetSpecialtieDTO save(VetSpecialtieDTO vetSpecialtieDTO) {
        log.debug("Request to save VetSpecialtie : {}", vetSpecialtieDTO);
        VetSpecialtie vetSpecialtie = vetSpecialtieMapper.toEntity(vetSpecialtieDTO);
        vetSpecialtie = vetSpecialtieRepository.save(vetSpecialtie);
        return vetSpecialtieMapper.toDto(vetSpecialtie);
    }

    @Override
    public Optional<VetSpecialtieDTO> partialUpdate(VetSpecialtieDTO vetSpecialtieDTO) {
        log.debug("Request to partially update VetSpecialtie : {}", vetSpecialtieDTO);

        return vetSpecialtieRepository
            .findById(vetSpecialtieDTO.getId())
            .map(existingVetSpecialtie -> {
                vetSpecialtieMapper.partialUpdate(existingVetSpecialtie, vetSpecialtieDTO);

                return existingVetSpecialtie;
            })
            .map(vetSpecialtieRepository::save)
            .map(vetSpecialtieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VetSpecialtieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VetSpecialties");
        return vetSpecialtieRepository.findAll(pageable).map(vetSpecialtieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VetSpecialtieDTO> findOne(Long id) {
        log.debug("Request to get VetSpecialtie : {}", id);
        return vetSpecialtieRepository.findById(id).map(vetSpecialtieMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VetSpecialtie : {}", id);
        vetSpecialtieRepository.deleteById(id);
    }
}
