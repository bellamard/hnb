package com.b2la.hnb.models;

import com.b2la.hnb.util.Etat; // Renommé en PascalCase
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturations") // Nom de table en minuscules
public class Facturation { // Nom de classe en PascalCase

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_reference", unique = true, nullable = false)
    private Long codeReference; // camelCase

    @Column(nullable = false)
    private double ttc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Etat etat; // Type renommé

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false) // Nom de colonne standard
    private Utilisateur utilisateur; // Type en PascalCase

    @ManyToOne
    @JoinColumn(name = "bilan_id", nullable = false)
    private Bilan bilan; // Type en PascalCase

    @OneToMany(mappedBy = "facturation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commande> commandes = new ArrayList<>(); // Relation bidirectionnelle

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_facturation")
    private Date dateFacturation; // camelCase

    public Facturation() {
    }

    // Constructeur sans la liste des commandes
    public Facturation(Long id, Long codeReference, double ttc, Etat etat,
                       Utilisateur utilisateur, Bilan bilan) {
        this.id = id;
        this.codeReference = codeReference;
        this.ttc = ttc;
        this.etat = etat;
        this.utilisateur = utilisateur;
        this.bilan = bilan;
    }

    // Getters et setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodeReference() {
        return codeReference;
    }

    public void setCodeReference(Long codeReference) {
        this.codeReference = codeReference;
    }

    public double getTtc() {
        return ttc;
    }

    public void setTtc(double ttc) {
        this.ttc = ttc;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Bilan getBilan() {
        return bilan;
    }

    public void setBilan(Bilan bilan) {
        this.bilan = bilan;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public Date getDateFacturation() {
        return dateFacturation;
    }

    public void setDateFacturation(Date dateFacturation) {
        this.dateFacturation = dateFacturation;
    }


}