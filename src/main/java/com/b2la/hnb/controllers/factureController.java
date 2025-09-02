package com.b2la.hnb.controllers;

import com.b2la.hnb.HelloApplication;
import com.b2la.hnb.models.Commande;
import com.b2la.hnb.models.Facturation;
import com.b2la.hnb.services.facturationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

import static javafx.application.Platform.runLater;

public class factureController {
    @FXML
    Label description;
    @FXML
    Button btnPrint;
    String detail;
    public Facturation facture;



    public void getDescription(Facturation fact){

        detail="Facture Ref: "+fact.getCodeReference()+
                "\nTotal: "+fact.getTtc()+" CDF \n";
        facturationService fs= new facturationService();
        Facturation factu= fs.findById(fact.getId());
        List<Commande> commandeList=factu.getCommandes();
        commandeList.forEach(commande -> detail+=commande.getProduit().getNom()+" | "+commande.getNombre()+" | "+commande.getPrixTotal()+" CDF \n");
        runLater(()->description.setText(detail));

        btnPrint.setOnAction(actionEvent -> printFacture(factu));
    }

    @FXML
    private void printFacture(Facturation factur){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("impression-view.fxml"));
            Parent rootPrint = loader.load();
            ImpressionController ic = loader.getController();
            ic.imprimer(factur);
            ic.lancerImpression(rootPrint);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
