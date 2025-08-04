package com.b2la.hnb.services;

import com.b2la.hnb.models.Produit;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class produitService {

    public void save(Produit produit){
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
    public void update(Produit produit){
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
    public Produit findById(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        Produit produit=null;
        try {
            produit=em.find(Produit.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }

        return produit;
    }

    public List<Produit> findAll(){
        EntityManager em= JPAUtil.getEntityManager();
        List<Produit> produitsList= null;
        try {
            TypedQuery<Produit> query= em.createQuery("SELECT p FROM Produits p", Produit.class);
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
            Produit produit= em.find(Produit.class, id);
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
