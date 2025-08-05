package com.b2la.hnb.controllers;

import com.b2la.hnb.models.Utilisateur;
import com.b2la.hnb.services.utilisateurService;
import com.b2la.hnb.util.BcryptUtil;
import com.b2la.hnb.util.Fonction;

public class utilisateurController {

    utilisateurService us;

    public void addUtilisateur(){
        String password="123456";
        Utilisateur user=new Utilisateur(
                "admin", Fonction.Admin,
                "895127236",
                BcryptUtil.hashPassword(password),
                "belamard@gmail.com"
        );
        us= new utilisateurService();
        us.save(user);
    }
}
