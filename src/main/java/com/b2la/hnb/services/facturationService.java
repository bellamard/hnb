package com.b2la.hnb.services;

import com.b2la.hnb.models.Bilan;
import com.b2la.hnb.models.Facturation;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.security.SecureRandom;
import java.util.List;

public class facturationService {

    private static final int LENGTH = 15;
    private final SecureRandom random = new SecureRandom();
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

    public List<Facturation> findByBilan(Bilan bilan) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Facturation> facturesList = null;
        try {
            TypedQuery<Facturation> query = em.createQuery(
                    "SELECT b FROM Bilan b WHERE b.Bilan = :bilan", Facturation.class);
            query.setParameter("bilan", bilan);
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

    public String generateNumericCode() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(random.nextInt(10)); // Chiffres de 0 à 9
        }
        return sb.toString();
    }

    public double sommeFacture() {
        EntityManager em = JPAUtil.getEntityManager();
        double numbreFacture = 0;
        try {
            Double result = (Double) em.createQuery("SELECT SUM(f.ttc) FROM Facturation f")
                    .getSingleResult();
            numbreFacture = (result != null) ? result : 0.0;
        } finally {
            em.close();
        }


        return numbreFacture;
    }

}
