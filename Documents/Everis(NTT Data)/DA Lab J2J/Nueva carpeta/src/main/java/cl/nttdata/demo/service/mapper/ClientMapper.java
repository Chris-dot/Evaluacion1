package cl.nttdata.demo.service.mapper;

import cl.nttdata.demo.domain.Client;
import cl.nttdata.demo.service.dto.ClientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "address", source = "address", qualifiedByName = "name")
    ClientDTO toDto(Client s);
}
