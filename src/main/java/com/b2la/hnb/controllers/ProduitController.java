package com.b2la.hnb.controllers;

import com.b2la.hnb.models.Produit;
import com.b2la.hnb.services.produitService;
import com.b2la.hnb.util.categoryType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    TableColumn<Produit, String>Tnom, Tdescription;
    TableColumn<Produit, Integer>Tquantite;
    TableColumn<Produit, Double>Tprix;
    TableColumn<Produit, categoryType>Ttype;

    @FXML
    Button btnAdd, btnAllDelete, btnValider;

    produitService ps;

    public void getProduitAll(){
        ps=new produitService();
        List<Produit> produitList= ps.findAll();
        Tnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Tdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        Tprix.setCellValueFactory(new PropertyValueFactory<>("prix"));

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
