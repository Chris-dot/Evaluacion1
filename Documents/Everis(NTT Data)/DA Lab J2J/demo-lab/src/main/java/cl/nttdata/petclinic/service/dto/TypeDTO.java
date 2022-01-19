package cl.nttdata.petclinic.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link cl.nttdata.petclinic.domain.Type} entity.
 */
public class TypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 80)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeDTO)) {
            return false;
        }

        TypeDTO typeDTO = (TypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
