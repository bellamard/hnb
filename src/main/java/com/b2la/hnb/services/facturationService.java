package com.b2la.hnb.services;

import com.b2la.hnb.models.facturation;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class facturationService {
    public void save(facturation facture){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try{
            transaction= em.getTransaction();
            transaction.begin();
            em.persist(facture);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }

    }

    public void update(facturation facture){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            em.merge(facture);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }

    }

    public facturation findById(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        facturation facture= null;
        try{
            facture= em.find(facturation.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
        return facture;
    }

    public List<facturation> findAll(){

    }

    public void delete (Long id){

    }

}
