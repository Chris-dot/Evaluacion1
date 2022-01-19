package cl.nttdata.petclinic.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link cl.nttdata.petclinic.domain.VetSpecialtie} entity.
 */
public class VetSpecialtieDTO implements Serializable {

    private Long id;

    private VetDTO vet;

    private SpecialtieDTO specialtie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VetDTO getVet() {
        return vet;
    }

    public void setVet(VetDTO vet) {
        this.vet = vet;
    }

    public SpecialtieDTO getSpecialtie() {
        return specialtie;
    }

    public void setSpecialtie(SpecialtieDTO specialtie) {
        this.specialtie = specialtie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VetSpecialtieDTO)) {
            return false;
        }

        VetSpecialtieDTO vetSpecialtieDTO = (VetSpecialtieDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vetSpecialtieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VetSpecialtieDTO{" +
            "id=" + getId() +
            ", vet=" + getVet() +
            ", specialtie=" + getSpecialtie() +
            "}";
    }
}
