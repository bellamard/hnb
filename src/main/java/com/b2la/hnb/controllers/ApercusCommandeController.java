package com.b2la.hnb.controllers;

import com.b2la.hnb.models.Bilan;
import com.b2la.hnb.models.Commande;
import com.b2la.hnb.util.Etat;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class ApercusCommandeController {
    Bilan bilan;
    @FXML
    TableView<Commande> tableCommande;
    @FXML
    TableColumn<Commande, Long> colId;
    @FXML
    TableColumn<Commande, String> colArticle;
    @FXML
    TableColumn<Commande, Double> colNombre, colTotal;

    public void initialize(){
        getAllCommande();
    }

    public void take(Bilan bil){
        bilan=bil;
    }

    void getAllCommande(){

    }
}
