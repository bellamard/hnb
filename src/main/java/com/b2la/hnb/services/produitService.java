package com.b2la.hnb.services;

import com.b2la.hnb.models.produits;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class produitService {

    public void save(produits produit){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction= null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            em.persist(produit);
            transaction.commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }
    public void update(produits produit){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction= null;
        try{
            transaction= em.getTransaction();
            transaction.begin();
            em.merge(produit);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }
    public produits findById(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        produits produit=null;
        try {
            produit=em.find(produits.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }

        return produit;
    }

    public List<produits> findAll(){
        EntityManager em= JPAUtil.getEntityManager();
        List<produits> produitsList= null;
        try {
            TypedQuery<produits> query= em.createQuery("SELECT p FROM Produits p", produits.class);
            produitsList=query.getResultList();
        } finally {
            em.close();
        }
        return produitsList;
    }
    public void delete(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction=em.getTransaction();
            transaction.begin();
            produits produit= em.find(produits.class, id);
            if(produit!=null)em.remove(produit);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }
}
