package com.b2la.hnb.services;

import com.b2la.hnb.models.Utilisateur;
import com.b2la.hnb.util.BcryptUtil;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class utilisateurService {


    public void save(Utilisateur utilisateur) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(utilisateur);
            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }

    }

    public void update(Utilisateur utilisateur) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(utilisateur);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }

    }

    public Utilisateur findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Utilisateur utilisateur = null;
        try {
            utilisateur = em.find(Utilisateur.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return utilisateur;
    }

    public List<Utilisateur> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Utilisateur> utilisateursList = null;
        try {
            TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class);
            utilisateursList = query.getResultList();
        } finally {
            em.close();
        }
        return utilisateursList;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Utilisateur utilisateur = em.find(Utilisateur.class, id);
            if (utilisateur != null) em.remove(utilisateur);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public Utilisateur findByName(String name) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.username=:username", Utilisateur.class);
            query.setParameter("username", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }

    }

    public Utilisateur login(String name, String password) {
        try {
            Utilisateur user = findByName(name);
            if (user != null) {
                System.out.println(user);
                if (BcryptUtil.checkPassword(password, user.getMotDePasse())) return user;
                throw new RuntimeException("mot de passe incorrecte");
            }
            throw new RuntimeException("Utilisateur n'existe pas");


        } catch (RuntimeException e) {

            throw new RuntimeException(e);
        }

    }
}
