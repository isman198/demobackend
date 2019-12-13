package id.co.tech.cakra.pegadaian.demo.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Efek.
 */
@Entity
@Table(name = "efek")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Efek implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "kode_efek", length = 20, nullable = false, unique = true)
    private String kodeEfek;

    @NotNull
    @Size(max = 100)
    @Column(name = "nama_efek", length = 100, nullable = false)
    private String namaEfek;

    @DecimalMin(value = "0")
    @Column(name = "closing_price")
    private Double closingPrice;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @Column(name = "status_gadai")
    private Boolean statusGadai;

    @OneToMany(mappedBy = "efek")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HargaPenutupan> hargaPenutupans = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKodeEfek() {
        return kodeEfek;
    }

    public Efek kodeEfek(String kodeEfek) {
        this.kodeEfek = kodeEfek;
        return this;
    }

    public void setKodeEfek(String kodeEfek) {
        this.kodeEfek = kodeEfek;
    }

    public String getNamaEfek() {
        return namaEfek;
    }

    public Efek namaEfek(String namaEfek) {
        this.namaEfek = namaEfek;
        return this;
    }

    public void setNamaEfek(String namaEfek) {
        this.namaEfek = namaEfek;
    }

    public Double getClosingPrice() {
        return closingPrice;
    }

    public Efek closingPrice(Double closingPrice) {
        this.closingPrice = closingPrice;
        return this;
    }

    public void setClosingPrice(Double closingPrice) {
        this.closingPrice = closingPrice;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public Efek closingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
        return this;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public Boolean isStatusGadai() {
        return statusGadai;
    }

    public Efek statusGadai(Boolean statusGadai) {
        this.statusGadai = statusGadai;
        return this;
    }

    public void setStatusGadai(Boolean statusGadai) {
        this.statusGadai = statusGadai;
    }

    public Set<HargaPenutupan> getHargaPenutupans() {
        return hargaPenutupans;
    }

    public Efek hargaPenutupans(Set<HargaPenutupan> hargaPenutupans) {
        this.hargaPenutupans = hargaPenutupans;
        return this;
    }

    public Efek addHargaPenutupan(HargaPenutupan hargaPenutupan) {
        this.hargaPenutupans.add(hargaPenutupan);
        hargaPenutupan.setEfek(this);
        return this;
    }

    public Efek removeHargaPenutupan(HargaPenutupan hargaPenutupan) {
        this.hargaPenutupans.remove(hargaPenutupan);
        hargaPenutupan.setEfek(null);
        return this;
    }

    public void setHargaPenutupans(Set<HargaPenutupan> hargaPenutupans) {
        this.hargaPenutupans = hargaPenutupans;
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
        Efek efek = (Efek) o;
        if (efek.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), efek.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Efek{" +
            "id=" + getId() +
            ", kodeEfek='" + getKodeEfek() + "'" +
            ", namaEfek='" + getNamaEfek() + "'" +
            ", closingPrice=" + getClosingPrice() +
            ", closingDate='" + getClosingDate() + "'" +
            ", statusGadai='" + isStatusGadai() + "'" +
            "}";
    }
}
