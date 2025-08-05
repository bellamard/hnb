package com.b2la.hnb.services;

import com.b2la.hnb.models.Bilan;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class bilanService {

    public void save(Bilan bil) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(bil);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public void update(Bilan bil) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(bil);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public Bilan findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Bilan bil = null;
        try {
            bil = em.find(Bilan.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return bil;
    }

    public List<Bilan> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Bilan> bilanList = null;
        try {
            TypedQuery<Bilan> query = em.createQuery("SELECT b FROM Bilans b", Bilan.class);
            bilanList = query.getResultList();
        } finally {
            em.close();
        }
        return bilanList;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Bilan bil = em.find(Bilan.class, id);
            if (bil != null) em.remove(bil);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}
