package com.b2la.hnb.services;

import com.b2la.hnb.models.Facturation;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class facturationService {
    public void save(Facturation facture) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(facture);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }

    }

    public void update(Facturation facture) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(facture);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }

    }

    public Facturation findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Facturation facture = null;
        try {
            facture = em.find(Facturation.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return facture;
    }

    public List<Facturation> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Facturation> facturesList = null;
        try {
            TypedQuery<Facturation> query = em.createQuery("SELECT f FROM Facturations f", Facturation.class);
            facturesList = query.getResultList();
        } finally {
            em.close();
        }
        return facturesList;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Facturation facture = em.find(Facturation.class, id);
            if (facture != null) em.remove(facture);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

}
