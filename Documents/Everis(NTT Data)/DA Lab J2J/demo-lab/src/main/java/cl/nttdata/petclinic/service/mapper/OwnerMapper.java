package cl.nttdata.petclinic.service.mapper;

import cl.nttdata.petclinic.domain.Owner;
import cl.nttdata.petclinic.service.dto.OwnerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Owner} and its DTO {@link OwnerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OwnerMapper extends EntityMapper<OwnerDTO, Owner> {
    @Named("firstName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    OwnerDTO toDtoFirstName(Owner owner);
}
