package cl.nttdata.petclinic.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cl.nttdata.petclinic.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpecialtieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Specialtie.class);
        Specialtie specialtie1 = new Specialtie();
        specialtie1.setId(1L);
        Specialtie specialtie2 = new Specialtie();
        specialtie2.setId(specialtie1.getId());
        assertThat(specialtie1).isEqualTo(specialtie2);
        specialtie2.setId(2L);
        assertThat(specialtie1).isNotEqualTo(specialtie2);
        specialtie1.setId(null);
        assertThat(specialtie1).isNotEqualTo(specialtie2);
    }
}
