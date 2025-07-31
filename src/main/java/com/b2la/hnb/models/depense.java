package com.b2la.hnb.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
@Table(name = "Depenses")
public class depense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String intitule;
    @Column(nullable = false)
    String motif;
    @Column(nullable = false)
    String auteur;
    boolean is_valide=false;
    boolean is_canceled=false;
    @ManyToOne
    @JoinColumn(name = "idUtilisateur", referencedColumnName = "id")
    utilisateurs utilisateur;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date date;

    public depense() {
    }

    public depense(Long id, String intitule, String motif, String auteur, boolean is_valide, boolean is_canceled, utilisateurs utilisateur, Date date) {
        this.id = id;
        this.intitule = intitule;
        this.motif = motif;
        this.auteur = auteur;
        this.is_valide = is_valide;
        this.is_canceled = is_canceled;
        this.utilisateur = utilisateur;
        this.date = date;
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

    public boolean isIs_valide() {
        return is_valide;
    }

    public void setIs_valide(boolean is_valide) {
        this.is_valide = is_valide;
    }

    public boolean isIs_canceled() {
        return is_canceled;
    }

    public void setIs_canceled(boolean is_canceled) {
        this.is_canceled = is_canceled;
    }

    public utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
