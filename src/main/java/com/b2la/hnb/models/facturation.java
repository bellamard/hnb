package com.b2la.hnb.models;

import com.b2la.hnb.util.etat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Facturations")
public class facturation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    Long code_reference;

    @Column(nullable = false)
    double ttc;

    @Column(nullable = false)
    etat etat;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "idUtilisateur", referencedColumnName = "id")
    utilisateurs utilisateur;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<produits> produit= new ArrayList<>();

    @Column(nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date dateFacturation;

    public facturation() {
    }

    public facturation(Long id, Long code_reference, double ttc, etat etat, utilisateurs utilisateur, List<produits> produit, Date dateFacturation) {
        this.id = id;
        this.code_reference = code_reference;
        this.ttc = ttc;
        this.etat = etat;
        this.utilisateur = utilisateur;
        this.produit = produit;
        this.dateFacturation = dateFacturation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode_reference() {
        return code_reference;
    }

    public void setCode_reference(Long code_reference) {
        this.code_reference = code_reference;
    }

    public double getTtc() {
        return ttc;
    }

    public void setTtc(double ttc) {
        this.ttc = ttc;
    }

    public etat getEtat() {
        return etat;
    }

    public void setEtat(etat etat) {
        this.etat = etat;
    }

    public utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<produits> getProduit() {
        return produit;
    }

    public void setProduit(List<produits> produit) {
        this.produit = produit;
    }

    public Date getDateFacturation() {
        return dateFacturation;
    }

    public void setDateFacturation(Date dateFacturation) {
        this.dateFacturation = dateFacturation;
    }
}
