package com.b2la.hnb.services;

import com.b2la.hnb.models.promotion;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class promotionService {

    public void save (promotion promo){
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

    public void update (promotion promo){
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

    public promotion findById(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        promotion promo= null;
        try{
            promo= em.find(promotion.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
        return promo;
    }

    public List<promotion> findAll(){

    }

    public void delete(Long id){

    }
}
