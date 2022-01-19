package cl.nttdata.petclinic.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.nttdata.petclinic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpecialtieDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialtieDTO.class);
        SpecialtieDTO specialtieDTO1 = new SpecialtieDTO();
        specialtieDTO1.setId(1L);
        SpecialtieDTO specialtieDTO2 = new SpecialtieDTO();
        assertThat(specialtieDTO1).isNotEqualTo(specialtieDTO2);
        specialtieDTO2.setId(specialtieDTO1.getId());
        assertThat(specialtieDTO1).isEqualTo(specialtieDTO2);
        specialtieDTO2.setId(2L);
        assertThat(specialtieDTO1).isNotEqualTo(specialtieDTO2);
        specialtieDTO1.setId(null);
        assertThat(specialtieDTO1).isNotEqualTo(specialtieDTO2);
    }
}
