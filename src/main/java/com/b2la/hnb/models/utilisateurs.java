package com.b2la.hnb.models;

import com.b2la.hnb.util.fonction;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "Utilisateurs")
public class utilisateurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    fonction fonction;
    @Column(nullable = false)
    String phone;
    @Column(nullable = false)
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    String mots_de_passe;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    Date date_creation;
    boolean is_service=true;
    @Temporal(TemporalType.TIMESTAMP)
    Date derniere_connection;
}
