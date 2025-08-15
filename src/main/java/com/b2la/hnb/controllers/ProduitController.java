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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.util.List;

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

    produitService ps;

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
                Produit produit= getTableView().getItems().get(getIndex());
                System.out.println(produit.getNom());
                btnMod.setOnAction(e->{

                    getProduit(produit);
                });

                btnSup.setOnAction(e->{

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

    public void getProduitSearch(String search){

    }
    public void deleteProduit(Long id){

    }
    private void update(){


    }
    private void reset(){
        nbreArticle.setValue(0);
        category.setValue(categoryType.plat);
        articleField.setText("");
        prixField.setText("0");
        description.setText("");
    }
}
