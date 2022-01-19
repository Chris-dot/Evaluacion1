package cl.nttdata.petclinic.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpecialtieMapperTest {

    private SpecialtieMapper specialtieMapper;

    @BeforeEach
    public void setUp() {
        specialtieMapper = new SpecialtieMapperImpl();
    }
}
