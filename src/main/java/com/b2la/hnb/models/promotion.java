package com.b2la.hnb.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Promotions")
public class promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    double discount;
    @Column(nullable = false)
    Date debut;
    @Column(nullable = false)
    Date fin;
    @ManyToOne
    @JoinColumn(name = "idproduit", referencedColumnName = "id")
    @Column(nullable = false)
    produits produit;

    public promotion() {
    }
    public promotion(Long id, double discount, Date debut, Date fin, produits produit) {
        this.id = id;
        this.discount = discount;
        this.debut = debut;
        this.fin = fin;
        this.produit = produit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public produits getProduit() {
        return produit;
    }

    public void setProduit(produits produit) {
        this.produit = produit;
    }
}
