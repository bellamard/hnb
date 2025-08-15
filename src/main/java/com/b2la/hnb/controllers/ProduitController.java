package com.b2la.hnb.controllers;

import com.b2la.hnb.models.Produit;
import com.b2la.hnb.services.produitService;
import com.b2la.hnb.util.categoryType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProduitController {
    @FXML
    TextField research, articleField, prixField;
    @FXML
    TextArea description;
    @FXML
    ComboBox<Integer> nbreArticle;
    @FXML
    ComboBox<categoryType> category;
    @FXML
    TableView<Produit> tableProduit;
    TableColumn<Produit, String>Tnom, Tdescription, Taction;
    TableColumn<Produit, Integer>Tquantite;
    TableColumn<Produit, Double>Tprix;
    TableColumn<Produit, categoryType>Ttype;

    @FXML
    Button btnAdd, btnAllDelete, btnValider;
    Label messageAlert;

    produitService ps;
    Long idProduit;

    public void initialize() {
        getProduitAll();
    }

    public void getProduitAll(){
        ps=new produitService();
        List<Produit> produitList= ps.findAll();
        Tnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Tdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        Tprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        Tquantite.setCellValueFactory(new PropertyValueFactory<>("quantite_stock"));
        Ttype.setCellValueFactory(new PropertyValueFactory<>("type"));
        Taction.setCellFactory(col->new TableCell<>(){
            Button btnMod= new Button("Mod");
            Button btnSup= new Button("Sup");
            {
                Produit produitItem= getTableView().getItems().get(getIndex());
                System.out.println(produitItem.getNom());
                btnMod.setOnAction(e->{
                    getProduit(produitItem);
                });

                btnSup.setOnAction(e->{
                    askSupression(produitItem.getId());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty)setGraphic(null);
                else{
                    HBox boxBtn=new HBox(2);
                    boxBtn.getChildren().addAll(btnSup,btnMod);
                    setGraphic(boxBtn);

                }
            }
        });

        Task<ObservableList<Produit>> task= new Task<>(){

            @Override
            protected ObservableList<Produit> call() throws Exception {
                return FXCollections.observableArrayList(produitList);
            }
        };
        task.setOnSucceeded(e->tableProduit.setItems(task.getValue()));
        new Thread(task).start();
        resetProduit();
    }
    private void getProduit(Produit produit){
        idProduit=produit.getId();
        articleField.setText(produit.getNom());
        description.setText(produit.getDescription());
        prixField.setText(String.valueOf(produit.getPrixUnitaire()));
        nbreArticle.setValue(produit.getQuantiteStock());
        category.setValue(produit.getType());
    }

    private void resetProduit(){
        articleField.setText("");
        prixField.setText("");
        description.setText("");
        for (int i = 0; i < 1000; i++) {
            nbreArticle.getItems().add(i);
        }
        nbreArticle.setValue(0);
        category.getItems().addAll(categoryType.values());
        category.setValue(categoryType.plat);

    }

    @FXML
    public void getProduitSearch(String search){
        List<Produit> produitList=ps.findAll().stream().filter(produit ->
                produit.getNom().toLowerCase().contains(search.toLowerCase()) ||
                produit.getDescription().toLowerCase().contains(search.toLowerCase())||
                String.valueOf(produit.getPrixUnitaire()).toLowerCase().contains(search.toLowerCase())||
                String.valueOf(produit.getQuantiteStock()).toLowerCase().contains(search.toLowerCase())
        ).collect(Collectors.toList());

        Task<ObservableList<Produit>> task= new Task<ObservableList<Produit>>() {
            @Override
            protected ObservableList<Produit> call() throws Exception {
                return FXCollections.observableArrayList(produitList);
            }
        };
        task.setOnSucceeded(e->tableProduit.setItems(task.getValue()));
        new Thread(task).start();
        resetProduit();
    }
    @FXML
    public void deleteAllProduit(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous Supprimer tout les produits?");
        alert.setContentText("Cliquez sur Ok pour confirmer la suppression.");
        Optional<ButtonType> result= alert.showAndWait();
        if(result.isPresent()&& result.get()== ButtonType.OK){
            List<Produit> produitList=ps.findAll();
            for (Produit produit : produitList) {
                ps.delete(produit.getId());
            }
        }
        getProduitAll();

    }
    private void update(){
        if(articleField.getText().isEmpty())messageErreur("Votre champs nom est vide!!!");
        if(description.getText().isEmpty())messageErreur("Votre champs description est vide!!!");
        if(prixField.getText().isEmpty())messageErreur("votre champs prix est vide!!!");
        if(category.getValue().toString().isEmpty())messageErreur("Votre categorie est vide!!!");
        if(nbreArticle.getValue().toString().isEmpty())messageErreur("Votre nombre d'article est vide!!!");

    }

    private void messageErreur(String message){
        messageAlert.setText(message);
        throw new RuntimeException(message);
    }


    private void askSupression(Long produitId){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous Supprimer ?");
        alert.setContentText("Cliquez sur Ok pour confirmer la suppression.");
        Optional<ButtonType> result= alert.showAndWait();
        if(result.isPresent()&& result.get()== ButtonType.OK){
            ps.delete(produitId);
            System.out.println("suppression valider");
            getProduitAll();
        }
    }
}
