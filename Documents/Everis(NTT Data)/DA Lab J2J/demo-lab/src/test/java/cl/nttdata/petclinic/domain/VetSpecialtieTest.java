package cl.nttdata.petclinic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cl.nttdata.petclinic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VetSpecialtieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VetSpecialtie.class);
        VetSpecialtie vetSpecialtie1 = new VetSpecialtie();
        vetSpecialtie1.setId(1L);
        VetSpecialtie vetSpecialtie2 = new VetSpecialtie();
        vetSpecialtie2.setId(vetSpecialtie1.getId());
        assertThat(vetSpecialtie1).isEqualTo(vetSpecialtie2);
        vetSpecialtie2.setId(2L);
        assertThat(vetSpecialtie1).isNotEqualTo(vetSpecialtie2);
        vetSpecialtie1.setId(null);
        assertThat(vetSpecialtie1).isNotEqualTo(vetSpecialtie2);
    }
}
