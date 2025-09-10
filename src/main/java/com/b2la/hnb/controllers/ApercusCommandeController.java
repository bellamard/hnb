package com.b2la.hnb.controllers;

import com.b2la.hnb.models.Bilan;
import com.b2la.hnb.models.Commande;
import com.b2la.hnb.models.Facturation;
import com.b2la.hnb.services.bilanService;
import com.b2la.hnb.services.commandeService;
import com.b2la.hnb.services.facturationService;
import com.b2la.hnb.util.Etat;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class ApercusCommandeController {
    Bilan bilan;
    facturationService fs;
    bilanService bs;
    commandeService cs;
    @FXML
    TableView<Commande> tableCommande;
    @FXML
    TableColumn<Commande, Long> colId;
    @FXML
    TableColumn<Commande, String> colArticle;
    @FXML
    TableColumn<Commande, Double> colNombre, colTotal;

    public void initialize(){
        fs= new facturationService();
        cs= new commandeService();
        bs= new bilanService();

    }

    public void take(Bilan bil){
        bilan=bil;
        System.out.println(bil.getDebutBilan());
        getAllCommande();
    }

    void getAllCommande(){
        List<Commande> commandeList = new java.util.ArrayList<>(List.of());
           bilan.getFacturations().forEach(facturation -> {
               facturation=fs.findById(facturation.getId());
               commandeList.addAll(facturation.getCommandes());
           });

           List<Commande>commandesGroup;
           commandeList.forEach(commande -> if());
    }
}
