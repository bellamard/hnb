package com.b2la.hnb.services;

import com.b2la.hnb.models.Commande;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class commandeService {

    public void save(Commande article) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(article);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public void update(Commande article) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(article);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public Commande findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Commande article = null;
        try {
            article = em.find(Commande.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return article;
    }

    public List<Commande> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Commande> articlesList = null;
        try {
            TypedQuery<Commande> query = em.createQuery("SELECT c FROM Commandes c", Commande.class);
            articlesList = query.getResultList();
        } finally {
            em.close();
        }
        return articlesList;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Commande article = em.find(Commande.class, id);
            if (article != null) em.remove(article);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}
