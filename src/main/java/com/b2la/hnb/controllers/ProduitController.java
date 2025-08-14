package com.b2la.hnb.controllers;

import com.b2la.hnb.models.Produit;
import com.b2la.hnb.util.categoryType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.awt.*;

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
    Button btnAdd, btnAllDelete, btnValider;

    public void getProduitAll(){

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
