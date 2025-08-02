package com.b2la.hnb.services;

import com.b2la.hnb.models.produits;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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
    public produits produit(Long id){

    }
    public void delete(Long id){

    }
}
