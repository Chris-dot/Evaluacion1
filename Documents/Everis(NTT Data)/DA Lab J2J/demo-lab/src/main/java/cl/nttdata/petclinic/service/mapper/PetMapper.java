package cl.nttdata.petclinic.service.mapper;

import cl.nttdata.petclinic.domain.Pet;
import cl.nttdata.petclinic.service.dto.PetDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pet} and its DTO {@link PetDTO}.
 */
@Mapper(componentModel = "spring", uses = { OwnerMapper.class, TypeMapper.class })
public interface PetMapper extends EntityMapper<PetDTO, Pet> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "firstName")
    @Mapping(target = "type", source = "type", qualifiedByName = "name")
    PetDTO toDto(Pet s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PetDTO toDtoName(Pet pet);
}
