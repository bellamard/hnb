package com.b2la.hnb.services;

import com.b2la.hnb.models.Promotion;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class promotionService {

    public void save (Promotion promo){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction= null;
        try{
            transaction= em.getTransaction();
            transaction.begin();
            em.persist(promo);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }

    public void update (Promotion promo){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction= null;
        try{
            transaction= em.getTransaction();
            transaction.begin();
            em.merge(promo);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }

    }

    public Promotion findById(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        Promotion promo= null;
        try{
            promo= em.find(Promotion.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
        return promo;
    }

    public List<Promotion> findAll(){
        EntityManager em= JPAUtil.getEntityManager();
        List<Promotion> promotionsList= null;
        try {
            TypedQuery<Promotion> query=em.createQuery("SELECT p FROM Promotions p", Promotion.class);
            promotionsList= query.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
        return promotionsList;

    }

    public void delete(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try{
            transaction= em.getTransaction();
            transaction.begin();
            Promotion promo= em.find(Promotion.class, id);
            if(promo!=null)em.remove(promo);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}
