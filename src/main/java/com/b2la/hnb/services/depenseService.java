package com.b2la.hnb.services;

import com.b2la.hnb.models.depense;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class depenseService {

    public void save(depense depense){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try{
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(depense);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }

    public void update(depense depense){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            em.merge(depense);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    public depense findById(Long id){

    }

    public List<depense> findAll(){

    }

    public void delete(Long id){

    }
}
