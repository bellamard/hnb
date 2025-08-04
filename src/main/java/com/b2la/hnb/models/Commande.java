package com.b2la.hnb.models;

import jakarta.persistence.*;

@Entity
@Table(name = "commandes") // Nom de table en minuscules
public class Commande { // Nom de classe en PascalCase

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(name = "prix_unitaire", nullable = false)
    private double prixUnitaire; // camelCase

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

    public Commande(Long id, String nom, double prixUnitaire, int nombre,
                    double prixTotal, Facturation facturation, Produit produit) {
        this.id = id;
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.nombre = nombre;
        this.prixTotal = prixTotal;
        this.facturation = facturation;
        this.produit = produit;
    }

    // Getters et setters...

    // Méthode de calcul automatique du prix total
    public void calculerPrixTotal() {
        this.prixTotal = this.prixUnitaire * this.nombre;
    }
}