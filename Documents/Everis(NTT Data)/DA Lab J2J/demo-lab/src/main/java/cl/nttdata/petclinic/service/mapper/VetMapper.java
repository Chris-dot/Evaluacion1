package cl.nttdata.petclinic.service.mapper;

import cl.nttdata.petclinic.domain.Vet;
import cl.nttdata.petclinic.service.dto.VetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vet} and its DTO {@link VetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VetMapper extends EntityMapper<VetDTO, Vet> {
    @Named("firstName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    VetDTO toDtoFirstName(Vet vet);
}
