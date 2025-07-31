package com.b2la.hnb.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Bilans")
public class bilan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<facturation> factures= new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<depense> depenses= new ArrayList<>();
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date debutBilan;
    @Temporal(TemporalType.TIMESTAMP)
    Date finBilan;

    public bilan() {
    }

    public bilan(Long id, List<facturation> factures, List<depense> depenses, Date debutBilan, Date finBilan) {
        this.id = id;
        this.factures = factures;
        this.depenses = depenses;
        this.debutBilan = debutBilan;
        this.finBilan = finBilan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<facturation> getFactures() {
        return factures;
    }

    public void setFactures(List<facturation> factures) {
        this.factures = factures;
    }

    public List<depense> getDepenses() {
        return depenses;
    }

    public void setDepenses(List<depense> depenses) {
        this.depenses = depenses;
    }

    public Date getDebutBilan() {
        return debutBilan;
    }

    public void setDebutBilan(Date debutBilan) {
        this.debutBilan = debutBilan;
    }

    public Date getFinBilan() {
        return finBilan;
    }

    public void setFinBilan(Date finBilan) {
        this.finBilan = finBilan;
    }

}
