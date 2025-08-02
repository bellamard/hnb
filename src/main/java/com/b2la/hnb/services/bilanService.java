package com.b2la.hnb.services;

import com.b2la.hnb.models.bilan;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class bilanService {

    public void save(bilan bilan){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            em.persist(bilan);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!= null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }

    public void update(){

    }
    public bilan findById(Long id){

    }

    public List<bilan> findAll(){

    }

    public void delete(Long id){

    }
}
