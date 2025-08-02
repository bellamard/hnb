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
    @Column(nullable = false, unique = true)
    String name;
    @Column(nullable = false)
    fonction fonction;
    @Column(nullable = false, unique = true)
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

    public utilisateurs() {
    }

    public utilisateurs(Long id, String name, fonction fonction, String phone, String mots_de_passe, Date date_creation, boolean is_service, Date derniere_connection) {
        this.id = id;
        this.name = name;
        this.fonction = fonction;
        this.phone = phone;
        this.mots_de_passe = mots_de_passe;
        this.date_creation = date_creation;
        this.is_service = is_service;
        this.derniere_connection = derniere_connection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public fonction getFonction() {
        return fonction;
    }

    public void setFonction(fonction fonction) {
        this.fonction = fonction;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMots_de_passe() {
        return mots_de_passe;
    }

    public void setMots_de_passe(String mots_de_passe) {
        this.mots_de_passe = mots_de_passe;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public boolean isIs_service() {
        return is_service;
    }

    public void setIs_service(boolean is_service) {
        this.is_service = is_service;
    }

    public Date getDerniere_connection() {
        return derniere_connection;
    }

    public void setDerniere_connection(Date derniere_connection) {
        this.derniere_connection = derniere_connection;
    }
}
