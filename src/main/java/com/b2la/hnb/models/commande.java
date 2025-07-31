package com.b2la.hnb.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Commandes")
public class commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String nom;
    @Column(nullable = false)
    double prix_unitaire;
    @Column(nullable = false)
    int nombre;
    @Column(nullable = false)
    double prix_total;
    @ManyToOne
    @JoinColumn(name = "idFacture", referencedColumnName = "id")
    facturation facturation;

    public commande() {
    }

    public commande(Long id, String nom, double prix_unitaire, int nombre, double prix_total, facturation facturation) {
        this.id = id;
        this.nom = nom;
        this.prix_unitaire = prix_unitaire;
        this.nombre = nombre;
        this.prix_total = prix_total;
        this.facturation = facturation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public double getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }

    public facturation getFacturation() {
        return facturation;
    }

    public void setFacturation(facturation facturation) {
        this.facturation = facturation;
    }
}
