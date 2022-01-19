package cl.nttdata.petclinic.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.nttdata.petclinic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VetSpecialtieDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VetSpecialtieDTO.class);
        VetSpecialtieDTO vetSpecialtieDTO1 = new VetSpecialtieDTO();
        vetSpecialtieDTO1.setId(1L);
        VetSpecialtieDTO vetSpecialtieDTO2 = new VetSpecialtieDTO();
        assertThat(vetSpecialtieDTO1).isNotEqualTo(vetSpecialtieDTO2);
        vetSpecialtieDTO2.setId(vetSpecialtieDTO1.getId());
        assertThat(vetSpecialtieDTO1).isEqualTo(vetSpecialtieDTO2);
        vetSpecialtieDTO2.setId(2L);
        assertThat(vetSpecialtieDTO1).isNotEqualTo(vetSpecialtieDTO2);
        vetSpecialtieDTO1.setId(null);
        assertThat(vetSpecialtieDTO1).isNotEqualTo(vetSpecialtieDTO2);
    }
}
