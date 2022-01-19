package cl.nttdata.petclinic.service.impl;

import cl.nttdata.petclinic.domain.Specialtie;
import cl.nttdata.petclinic.repository.SpecialtieRepository;
import cl.nttdata.petclinic.service.SpecialtieService;
import cl.nttdata.petclinic.service.dto.SpecialtieDTO;
import cl.nttdata.petclinic.service.mapper.SpecialtieMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Specialtie}.
 */
@Service
@Transactional
public class SpecialtieServiceImpl implements SpecialtieService {

    private final Logger log = LoggerFactory.getLogger(SpecialtieServiceImpl.class);

    private final SpecialtieRepository specialtieRepository;

    private final SpecialtieMapper specialtieMapper;

    public SpecialtieServiceImpl(SpecialtieRepository specialtieRepository, SpecialtieMapper specialtieMapper) {
        this.specialtieRepository = specialtieRepository;
        this.specialtieMapper = specialtieMapper;
    }

    @Override
    public SpecialtieDTO save(SpecialtieDTO specialtieDTO) {
        log.debug("Request to save Specialtie : {}", specialtieDTO);
        Specialtie specialtie = specialtieMapper.toEntity(specialtieDTO);
        specialtie = specialtieRepository.save(specialtie);
        return specialtieMapper.toDto(specialtie);
    }

    @Override
    public Optional<SpecialtieDTO> partialUpdate(SpecialtieDTO specialtieDTO) {
        log.debug("Request to partially update Specialtie : {}", specialtieDTO);

        return specialtieRepository
            .findById(specialtieDTO.getId())
            .map(existingSpecialtie -> {
                specialtieMapper.partialUpdate(existingSpecialtie, specialtieDTO);

                return existingSpecialtie;
            })
            .map(specialtieRepository::save)
            .map(specialtieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpecialtieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Specialties");
        return specialtieRepository.findAll(pageable).map(specialtieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SpecialtieDTO> findOne(Long id) {
        log.debug("Request to get Specialtie : {}", id);
        return specialtieRepository.findById(id).map(specialtieMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Specialtie : {}", id);
        specialtieRepository.deleteById(id);
    }
}
