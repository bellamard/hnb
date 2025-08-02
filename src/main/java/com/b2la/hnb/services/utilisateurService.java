package com.b2la.hnb.services;

import com.b2la.hnb.models.utilisateurs;
import com.b2la.hnb.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class utilisateurService {

    public void save(utilisateurs utilisateur){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            em.persist(utilisateur);
            transaction.commit();

        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }

    }

    public void update(utilisateurs utilisateur){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            em.merge(utilisateur);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }

    }

    public utilisateurs findById(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        utilisateurs utilisateur= null;
        try {
            utilisateur= em.find(utilisateurs.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
        return utilisateur;
    }

    public List<utilisateurs> findAll(){
        EntityManager em= JPAUtil.getEntityManager();
        List<utilisateurs> utilisateursList= null;
        try {
            TypedQuery<utilisateurs> query= em.createQuery("SELECT u FROM Utilisateurs u", utilisateurs.class);
            utilisateursList=query.getResultList();
        } finally {
            em.close();
        }
        return utilisateursList;
    }

    public void delete(Long id){
        EntityManager em= JPAUtil.getEntityManager();
        EntityTransaction transaction=null;
        try {
            transaction= em.getTransaction();
            transaction.begin();
            utilisateurs utilisateur=em.find(utilisateurs.class, id);
            if(utilisateur!=null)em.remove(utilisateur);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction!=null)transaction.rollback();
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }
}
