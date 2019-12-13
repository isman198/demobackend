package id.co.tech.cakra.pegadaian.demo.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HargaPenutupan.
 */
@Entity
@Table(name = "harga_penutupan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HargaPenutupan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tanggal", nullable = false)
    private LocalDate tanggal;

    @DecimalMin(value = "0")
    @Column(name = "harga")
    private Double harga;

    @ManyToOne
    @JsonIgnoreProperties("hargaPenutupans")
    private Efek efek;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public HargaPenutupan tanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
        return this;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public Double getHarga() {
        return harga;
    }

    public HargaPenutupan harga(Double harga) {
        this.harga = harga;
        return this;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public Efek getEfek() {
        return efek;
    }

    public HargaPenutupan efek(Efek efek) {
        this.efek = efek;
        return this;
    }

    public void setEfek(Efek efek) {
        this.efek = efek;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HargaPenutupan hargaPenutupan = (HargaPenutupan) o;
        if (hargaPenutupan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hargaPenutupan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HargaPenutupan{" +
            "id=" + getId() +
            ", tanggal='" + getTanggal() + "'" +
            ", harga=" + getHarga() +
            "}";
    }
}
