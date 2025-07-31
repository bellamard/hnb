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

}
