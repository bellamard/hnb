package com.b2la.hnb.services;

import com.b2la.hnb.models.depense;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

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
        }finally {
            em.close();
        }
    }

    public depense findById(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        depense depense= null;
        try {
            depense= em.find(depense.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
        return depense;
    }

    public List<depense> findAll(){
        EntityManager em= JPAUtil.getEntityManager();
        List<depense> depenseList= null;

        try{
            TypedQuery<depense> query= em.createQuery("SELECT d FROM Depenses d", depense.class);
            depenseList= query.getResultList();
        } finally {
            em.close();
        }
        return  depenseList;

    }

    public void delete(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            depense depense= em.find(depense.class, id);
            if(depense!=null)em.remove(depense);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }
}
