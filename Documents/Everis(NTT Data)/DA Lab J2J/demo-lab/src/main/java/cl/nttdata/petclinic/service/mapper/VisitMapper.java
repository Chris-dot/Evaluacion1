package cl.nttdata.petclinic.service.mapper;

import cl.nttdata.petclinic.domain.Visit;
import cl.nttdata.petclinic.service.dto.VisitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Visit} and its DTO {@link VisitDTO}.
 */
@Mapper(componentModel = "spring", uses = { PetMapper.class })
public interface VisitMapper extends EntityMapper<VisitDTO, Visit> {
    @Mapping(target = "pet", source = "pet", qualifiedByName = "name")
    VisitDTO toDto(Visit s);
}
