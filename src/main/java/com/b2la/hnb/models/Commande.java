package com.b2la.hnb.models;

import jakarta.persistence.*;

@Entity
@Table(name = "commandes") // Nom de table en minuscules
public class Commande { // Nom de classe en PascalCase

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int nombre;

    @Column(name = "prix_total", nullable = false)
    private double prixTotal; // camelCase

    @ManyToOne
    @JoinColumn(name = "facturation_id", nullable = false)
    private Facturation facturation; // Type en PascalCase

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit; // Relation avec Produit

    public Commande() {
    }

    public Commande(Long id, int nombre, double prixTotal, Facturation facturation, Produit produit) {
        this.id = id;
        this.nombre = nombre;
        this.prixTotal = prixTotal;
        this.facturation = facturation;
        this.produit = produit;
    }

    // Getters et setters...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Facturation getFacturation() {
        return facturation;
    }

    public void setFacturation(Facturation facturation) {
        this.facturation = facturation;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    // Méthode de calcul automatique du prix total
    public void calculerPrixTotal() {
        this.prixTotal = this.produit.getPrixUnitaire() * this.nombre;
    }
}