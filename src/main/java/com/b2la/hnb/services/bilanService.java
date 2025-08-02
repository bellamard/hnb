package com.b2la.hnb.services;

import com.b2la.hnb.models.bilan;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class bilanService {

    public void save(bilan bil){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            em.persist(bil);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!= null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }

    public void update(bilan bil){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction=em.getTransaction();
            transaction.begin();
            em.merge(bil);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }

    public bilan findById(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        bilan bil= null;
        try{
            bil=em.find(bilan.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
        return bil;
    }

    public List<bilan> findAll(){

    }

    public void delete(Long id){

    }
}
