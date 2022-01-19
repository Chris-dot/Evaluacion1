package cl.nttdata.petclinic.service.mapper;

import cl.nttdata.petclinic.domain.Type;
import cl.nttdata.petclinic.service.dto.TypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Type} and its DTO {@link TypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeMapper extends EntityMapper<TypeDTO, Type> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TypeDTO toDtoName(Type type);
}
