package cl.nttdata.petclinic.service.mapper;

import cl.nttdata.petclinic.domain.VetSpecialtie;
import cl.nttdata.petclinic.service.dto.VetSpecialtieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VetSpecialtie} and its DTO {@link VetSpecialtieDTO}.
 */
@Mapper(componentModel = "spring", uses = { VetMapper.class, SpecialtieMapper.class })
public interface VetSpecialtieMapper extends EntityMapper<VetSpecialtieDTO, VetSpecialtie> {
    @Mapping(target = "vet", source = "vet", qualifiedByName = "firstName")
    @Mapping(target = "specialtie", source = "specialtie", qualifiedByName = "name")
    VetSpecialtieDTO toDto(VetSpecialtie s);
}
