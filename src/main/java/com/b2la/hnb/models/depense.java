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
}
