package com.b2la.hnb.models;

import com.b2la.hnb.util.Fonction; // Renommé en PascalCase
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "utilisateurs") // Nom de table en minuscules
public class Utilisateur { // Nom de classe en PascalCase

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true, length = 50)
    private String username; // Renommé pour la cohérence avec les standards

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Fonction fonction;


    @Column(nullable = false, unique = true, length = 20)
    private String telephone; // Renommé pour la cohérence

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    private String motDePasse; // camelCase et sera chiffré

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation", updatable = false)
    private Date dateCreation; // camelCase

    @Column(name = "is_active")
    private boolean actif = true; // Renommé pour plus de clarté

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "derniere_connexion")
    private Date derniereConnexion; // camelCase

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_modification")
    private Date dateModification;


    @Column(unique = true, length = 100)
    private String email; // Nouveau champ important

    // Constructeurs
    public Utilisateur() {
    }

    public Utilisateur(String username, Fonction fonction, String telephone,
                       String motDePasse, String email) {
        this.username = username;
        this.fonction = fonction;
        this.telephone = telephone;
        this.setMotDePasse(motDePasse); // Chiffrement du mot de passe
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getMotDePasse() {
        return motDePasse;
    }



    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    // Méthodes métier
    public void desactiverCompte() {
        this.actif = false;
        this.dateModification = new Date();
    }

    public void mettreAJourConnexion() {
        this.derniereConnexion = new Date();
    }

    // toString() pour le débogage
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fonction=" + fonction +
                ", email='" + email + '\'' +
                ", actif=" + actif +
                '}';
    }
}