package com.b2la.hnb.services;

import com.b2la.hnb.models.Depense;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class depenseService {

    public void save(Depense depense) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(depense);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public void update(Depense depense) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(depense);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public Depense findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Depense depense = null;
        try {
            depense = em.find(Depense.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return depense;
    }

    public List<Depense> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Depense> depenseList = null;

        try {
            TypedQuery<Depense> query = em.createQuery("SELECT d FROM Depenses d", Depense.class);
            depenseList = query.getResultList();
        } finally {
            em.close();
        }
        return depenseList;

    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Depense depense = em.find(Depense.class, id);
            if (depense != null) em.remove(depense);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}
