package com.b2la.hnb.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "depenses") // Nom de table en minuscules
public class Depense { // Nom de classe en PascalCase
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String intitule;

    @Column(nullable = false)
    private String motif;

    @Column(nullable = false)
    private String auteur;

    @Column(nullable = false)
    private Double montant;

    @Column(name = "is_valide")
    private boolean valide = false; // Renommé et camelCase

    @Column(name = "is_canceled")
    private boolean annulee = false; // Renommé et camelCase

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur; // Type en PascalCase

    @ManyToOne
    @JoinColumn(name = "bilan_id")
    private Bilan bilan; // Relation avec Bilan

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    public Depense() {
    }

    public Depense(String intitule, String motif, String auteur, Utilisateur utilisateur, Double montant) {
        this.intitule = intitule;
        this.motif = motif;
        this.auteur = auteur;
        this.utilisateur = utilisateur;
        this.montant = montant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Bilan getBilan() {
        return bilan;
    }

    public void setBilan(Bilan bilan) {
        this.bilan = bilan;
    }
    // Getters et setters...

    // Méthodes booléennes plus idiomatiques
    public boolean estValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public boolean estAnnulee() {
        return annulee;
    }

    public void setAnnulee(boolean annulee) {
        this.annulee = annulee;
    }
}