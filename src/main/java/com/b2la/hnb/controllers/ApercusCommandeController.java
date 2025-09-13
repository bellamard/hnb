package com.b2la.hnb.controllers;

import com.b2la.hnb.models.Bilan;
import com.b2la.hnb.models.Commande;
import com.b2la.hnb.models.Facturation;
import com.b2la.hnb.services.bilanService;
import com.b2la.hnb.services.commandeService;
import com.b2la.hnb.services.facturationService;
import com.b2la.hnb.util.Etat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ApercusCommandeController {
    Bilan bilan;
    facturationService fs;
    bilanService bs;
    commandeService cs;
    @FXML
    TextField searchField;
    @FXML
    TableView<Commande> tableCommande;
    @FXML
    TableColumn<Commande, Long> colId;
    @FXML
    TableColumn<Commande, String> colArticle;
    @FXML
    TableColumn<Commande, Double> colNombre, colTotal;

    public void initialize() {
        fs = new facturationService();
        cs = new commandeService();
        bs = new bilanService();

    }

    public void take(Bilan bil) {
        bilan = bil;
        System.out.println(bil.getDebutBilan());
        getAllCommande();
    }

    void getAllCommande() {
        List<Commande> commandeList = new ArrayList<>(List.of());
        bilan.getFacturations().forEach(facturation -> {
            facturation = fs.findById(facturation.getId());
            commandeList.addAll(facturation.getCommandes());
        });

        List<Commande> commandesGroup = new ArrayList<>(
                commandeList.stream()
                        .collect(Collectors.groupingBy(
                                c -> c.getProduit().getId(),
                                Collectors.reducing((c1, c2) -> {
                                    c1.setPrixTotal(c1.getPrixTotal() + c2.getPrixTotal());
                                    c1.setNombre(c1.getNombre() + c2.getNombre());
                                    return c1;
                                })
                        ))
                        .values()
                        .stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
        );
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        colArticle.setCellFactory(col -> new TableCell<>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty)setGraphic(null);
                else {
                    Label nomCommande= new Label(getTableView().getItems().get(getIndex()).getProduit().getNom());
                    HBox boxText= new HBox(1);
                    boxText.getChildren().add(nomCommande);
                    setGraphic(boxText);
                }
            }
        });
        Task<ObservableList<Commande>>task=new Task<>() {
            @Override
            protected ObservableList<Commande> call() throws Exception {
                return FXCollections.observableArrayList(commandesGroup);
            }
        };
        task.setOnSucceeded(e ->tableCommande.setItems(task.getValue()) );
        new Thread(task).start();



    }
    @FXML
    void getSearchCommande() {
        List<Commande> commandeList = new ArrayList<>(List.of());
        bilan.getFacturations().forEach(facturation -> {
            facturation = fs.findById(facturation.getId());
            commandeList.addAll(facturation.getCommandes());
        });

        List<Commande> commandesGroup = new ArrayList<>(
                commandeList.stream()
                        .collect(Collectors.groupingBy(
                                c -> c.getProduit().getId(),
                                Collectors.reducing((c1, c2) -> {
                                    c1.setPrixTotal(c1.getPrixTotal() + c2.getPrixTotal());
                                    c1.setNombre(c1.getNombre() + c2.getNombre());
                                    return c1;
                                })
                        ))
                        .values()
                        .stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
        );

        Task<ObservableList<Commande>>task=new Task<>() {
            @Override
            protected ObservableList<Commande> call() throws Exception {
                return FXCollections.observableArrayList(commandesGroup.stream().filter(commande -> commande.getProduit().getNom().toLowerCase().contains(searchField.getText().toLowerCase())
                        ||String.valueOf(commande.getNombre()).contains(searchField.getText())
                        ||String.valueOf(commande.getPrixTotal()).contains(searchField.getText())).collect(Collectors.toList()));
            }
        };
        task.setOnSucceeded(e ->tableCommande.setItems(task.getValue()) );
        new Thread(task).start();



    }


}
