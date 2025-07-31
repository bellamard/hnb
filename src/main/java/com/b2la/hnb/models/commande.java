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

}
