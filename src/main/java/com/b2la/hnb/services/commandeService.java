package com.b2la.hnb.services;

import com.b2la.hnb.models.commande;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class commandeService {

    public void save(commande article){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction= null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            em.persist(article);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }

    public void update(commande article){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            em.merge(article);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }

    public commande findById(Long id){

    }

    public List<commande> findAll(){
        EntityManager em = JPAUtil.getEntityManager();
        List<commande> articlesList= null;
        try {
            TypedQuery<commande> query= em.createQuery("SELECT c FROM Commandes c", commande.class);
            articlesList= query.getResultList();
        }finally {
            em.close();
        }
        return articlesList;
    }

    public void delete(Long id){

    }
}
