package com.b2la.hnb.controllers;

import com.b2la.hnb.HelloApplication;
import com.b2la.hnb.models.Commande;
import com.b2la.hnb.models.Facturation;
import com.b2la.hnb.models.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ImpressionController {
    @FXML
    private Label labelDateFacture, labelNumeroFacture, labelTotal, labelPaiement;
    @FXML
    private VBox articlesContainer;

    public void imprimer(Facturation fac) {


        labelDateFacture.setText("Date :" + fac.getDateFacturation().toString());
        labelNumeroFacture.setText("Facture N° :" + fac.getCodeReference());
        labelTotal.setText("Total : " + fac.getTtc() + " CDF");
        labelPaiement.setText("Paiement : " + fac.getEtat());
        List<Commande> commandeList = fac.getCommandes();
        commandeList.forEach(commande -> {ajouterArticle(commande.getProduit().getNom(), commande.getNombre(), commande.getPrixTotal());});


    }

    /**
     * @param nom      Nom de l'article
     * @param quantite Quantité achetée
     * @param prix     Prix total (quantité x prix unitaire)
     */
    public void ajouterArticle(String nom, int quantite, double prix) {
        HBox ligne = new HBox(10);
        ligne.setPrefWidth(220);

        Label lblNom = new Label(nom);
        lblNom.setPrefWidth(80);

        Label lblQuantite = new Label(String.valueOf(quantite));
        lblQuantite.setPrefWidth(40);
        lblQuantite.setStyle("-fx-alignment: center-right;");

        Label lblPrix = new Label(String.format("%.2f$", prix));
        lblPrix.setPrefWidth(80);
        lblPrix.setStyle("-fx-alignment: center-right;");

        ligne.getChildren().addAll(lblNom, lblQuantite, lblPrix);
        articlesContainer.getChildren().add(ligne);
    }

    public void lancerImpression(Node rootNode) {
        Printer defaultPrinter = Printer.getDefaultPrinter();
        if (defaultPrinter == null) {
            System.out.println("Aucune imprimante par défaut trouvée !");
            throw new RuntimeException("Aucune imprimante par défaut trouvée !");
        }
        PrinterJob job = PrinterJob.createPrinterJob();
        if(job==null){
            System.out.println("Impossible de créer un travail d'impression !");
            throw new RuntimeException("Impossible de créer un travail d'impression !");

        }
        boolean proceed= job.showPageSetupDialog(null);
        if(proceed){
            boolean sucess =job.printPage(rootNode);
            if (sucess) {
                job.endJob();
            }else{
                throw new RuntimeException("Échec de l'impression !");
            }

        }

    }
}
