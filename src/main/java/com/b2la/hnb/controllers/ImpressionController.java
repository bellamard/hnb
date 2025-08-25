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
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ImpressionController {
    @FXML
    private Label labelDateFacture, labelNumeroFacture, labelTotal, labelPaiement;
    @FXML
    private TableView<Commande> tableArticles;
    @FXML
    private TableColumn<Commande, String> colNom;
    @FXML
    private TableColumn<Commande, Integer> colQuantite;
    @FXML
    private TableColumn<Commande, Integer> colPrix;

    public void imprimer(Facturation fac) {


            labelDateFacture.setText("Date :" + fac.getDateFacturation().toString());
            labelNumeroFacture.setText("Facture N° :" + fac.getCodeReference());
            labelTotal.setText("Total : " + fac.getTtc() + " CDF");
            labelPaiement.setText("Paiement : " + fac.getEtat());
            List<Commande> commandeList = fac.getCommandes();
            colNom.setCellFactory(col -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) setGraphic(null);
                    else {
                        Produit orderCommande = getTableView().getItems().get(getIndex()).getProduit();
                        Label nomProduit = new Label(orderCommande.getNom());
                        HBox boxText = new HBox(0);
                        boxText.getChildren().add(nomProduit);
                        setGraphic(boxText);
                    }
                }
            });
            colQuantite.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colPrix.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));

            Task<ObservableList<Commande>> task = new Task<>() {
                @Override
                protected ObservableList<Commande> call() throws Exception {
                    return FXCollections.observableArrayList(commandeList);
                }
            };
            task.setOnSucceeded(e -> tableArticles.setItems(task.getValue()));
            new Thread(task).start();



    }

    public void lancerImpression(Node rootNode) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.printPage(rootNode)) {
            job.endJob();
        }
    }
}
