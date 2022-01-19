package cl.nttdata.petclinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Vet.
 */
@Entity
@Table(name = "vet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 30)
    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "vet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vet", "specialtie" }, allowSetters = true)
    private Set<VetSpecialtie> specialties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Vet firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Vet lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<VetSpecialtie> getSpecialties() {
        return this.specialties;
    }

    public void setSpecialties(Set<VetSpecialtie> vetSpecialties) {
        if (this.specialties != null) {
            this.specialties.forEach(i -> i.setVet(null));
        }
        if (vetSpecialties != null) {
            vetSpecialties.forEach(i -> i.setVet(this));
        }
        this.specialties = vetSpecialties;
    }

    public Vet specialties(Set<VetSpecialtie> vetSpecialties) {
        this.setSpecialties(vetSpecialties);
        return this;
    }

    public Vet addSpecialtie(VetSpecialtie vetSpecialtie) {
        this.specialties.add(vetSpecialtie);
        vetSpecialtie.setVet(this);
        return this;
    }

    public Vet removeSpecialtie(VetSpecialtie vetSpecialtie) {
        this.specialties.remove(vetSpecialtie);
        vetSpecialtie.setVet(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vet)) {
            return false;
        }
        return id != null && id.equals(((Vet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vet{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
