package cl.nttdata.petclinic.service.mapper;

import cl.nttdata.petclinic.domain.Specialtie;
import cl.nttdata.petclinic.service.dto.SpecialtieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Specialtie} and its DTO {@link SpecialtieDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpecialtieMapper extends EntityMapper<SpecialtieDTO, Specialtie> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SpecialtieDTO toDtoName(Specialtie specialtie);
}
