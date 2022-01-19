package cl.nttdata.petclinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VetSpecialtie.
 */
@Entity
@Table(name = "vet_specialtie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VetSpecialtie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "specialties" }, allowSetters = true)
    private Vet vet;

    @ManyToOne(optional = false)
    @NotNull
    private Specialtie specialtie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VetSpecialtie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vet getVet() {
        return this.vet;
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public VetSpecialtie vet(Vet vet) {
        this.setVet(vet);
        return this;
    }

    public Specialtie getSpecialtie() {
        return this.specialtie;
    }

    public void setSpecialtie(Specialtie specialtie) {
        this.specialtie = specialtie;
    }

    public VetSpecialtie specialtie(Specialtie specialtie) {
        this.setSpecialtie(specialtie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VetSpecialtie)) {
            return false;
        }
        return id != null && id.equals(((VetSpecialtie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VetSpecialtie{" +
            "id=" + getId() +
            "}";
    }
}
