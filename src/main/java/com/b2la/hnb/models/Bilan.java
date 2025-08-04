package com.b2la.hnb.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bilans") // Nom de table en minuscules
public class Bilan { // Nom de classe en PascalCase
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "bilan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Facturation> facturations = new ArrayList<>(); // Relation bidirectionnelle

    @OneToMany(mappedBy = "bilan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Depense> depenses = new ArrayList<>(); // Relation bidirectionnelle

    @ManyToMany
    @JoinTable(
            name = "bilan_produits",
            joinColumns = @JoinColumn(name = "bilan_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id")
    )
    private List<Produit> produits = new ArrayList<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "debut_bilan")
    private Date debutBilan; // camelCase

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fin_bilan")
    private Date finBilan; // camelCase

    public Bilan() {
    }

    // Constructeur simplifié sans les listes
    public Bilan(Date debutBilan, Date finBilan) {
        this.debutBilan = debutBilan;
        this.finBilan = finBilan;
    }

    // Méthodes utilitaires
    public void addFacturation(Facturation facturation) {
        facturations.add(facturation);
        facturation.setBilan(this);
    }

    public void removeFacturation(Facturation facturation) {
        facturations.remove(facturation);
        facturation.setBilan(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Facturation> getFacturations() {
        return facturations;
    }

    public void setFacturations(List<Facturation> facturations) {
        this.facturations = facturations;
    }

    public List<Depense> getDepenses() {
        return depenses;
    }

    public void setDepenses(List<Depense> depenses) {
        this.depenses = depenses;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
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