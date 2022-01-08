package com.bedir.mywords.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Deste.
 */
@Entity
@Table(name = "deste")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Deste implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "renk")
    private String renk;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    @OneToMany(mappedBy = "tekDeste")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Kart> kartlars = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRenk() {
        return renk;
    }

    public Deste renk(String renk) {
        this.renk = renk;
        return this;
    }

    public void setRenk(String renk) {
        this.renk = renk;
    }

    public User getInternalUser() {
        return internalUser;
    }

    public Deste internalUser(User user) {
        this.internalUser = user;
        return this;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Set<Kart> getKartlars() {
        return kartlars;
    }

    public Deste kartlars(Set<Kart> karts) {
        this.kartlars = karts;
        return this;
    }

    public Deste addKartlar(Kart kart) {
        this.kartlars.add(kart);
        kart.setTekDeste(this);
        return this;
    }

    public Deste removeKartlar(Kart kart) {
        this.kartlars.remove(kart);
        kart.setTekDeste(null);
        return this;
    }

    public void setKartlars(Set<Kart> karts) {
        this.kartlars = karts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deste)) {
            return false;
        }
        return id != null && id.equals(((Deste) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deste{" +
            "id=" + getId() +
            ", renk='" + getRenk() + "'" +
            "}";
    }
}
