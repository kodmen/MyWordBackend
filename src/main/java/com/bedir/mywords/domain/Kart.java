package com.bedir.mywords.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Kart.
 */
@Entity
@Table(name = "kart")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Kart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "on_yuz")
    private String onYuz;

    @Column(name = "arka_yuz")
    private String arkaYuz;

    @Column(name = "onem_sira")
    private Integer onemSira;

    @ManyToOne
    @JsonIgnoreProperties(value = "kartlars", allowSetters = true)
    private Deste tekDeste;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOnYuz() {
        return onYuz;
    }

    public Kart onYuz(String onYuz) {
        this.onYuz = onYuz;
        return this;
    }

    public void setOnYuz(String onYuz) {
        this.onYuz = onYuz;
    }

    public String getArkaYuz() {
        return arkaYuz;
    }

    public Kart arkaYuz(String arkaYuz) {
        this.arkaYuz = arkaYuz;
        return this;
    }

    public void setArkaYuz(String arkaYuz) {
        this.arkaYuz = arkaYuz;
    }

    public Integer getOnemSira() {
        return onemSira;
    }

    public Kart onemSira(Integer onemSira) {
        this.onemSira = onemSira;
        return this;
    }

    public void setOnemSira(Integer onemSira) {
        this.onemSira = onemSira;
    }

    public Deste getTekDeste() {
        return tekDeste;
    }

    public Kart tekDeste(Deste deste) {
        this.tekDeste = deste;
        return this;
    }

    public void setTekDeste(Deste deste) {
        this.tekDeste = deste;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kart)) {
            return false;
        }
        return id != null && id.equals(((Kart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kart{" +
            "id=" + getId() +
            ", onYuz='" + getOnYuz() + "'" +
            ", arkaYuz='" + getArkaYuz() + "'" +
            ", onemSira=" + getOnemSira() +
            "}";
    }
}
