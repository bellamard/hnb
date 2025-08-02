package com.b2la.hnb.services;

import com.b2la.hnb.models.commande;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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

    }

    public void delete(Long id){

    }
}
