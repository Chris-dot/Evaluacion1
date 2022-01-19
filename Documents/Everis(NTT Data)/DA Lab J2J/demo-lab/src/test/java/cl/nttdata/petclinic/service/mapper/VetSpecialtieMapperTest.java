package cl.nttdata.petclinic.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VetSpecialtieMapperTest {

    private VetSpecialtieMapper vetSpecialtieMapper;

    @BeforeEach
    public void setUp() {
        vetSpecialtieMapper = new VetSpecialtieMapperImpl();
    }
}
