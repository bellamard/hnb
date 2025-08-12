package com.b2la.hnb.controllers;

import com.b2la.hnb.models.Utilisateur;
import com.b2la.hnb.services.utilisateurService;
import com.b2la.hnb.util.BcryptUtil;
import com.b2la.hnb.util.Fonction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class utilisateurController {

    utilisateurService us;
    @FXML
    TableColumn<Utilisateur, String> username, phone, email,action;
    @FXML
    TableColumn<Utilisateur, Fonction> fonction;

    @FXML
    TextField fieldUsername, fieldPhone, fieldEmail, password, confirme;
    @FXML
    ComboBox<Fonction> comboFonction;
    Long id;


    public void initialize() {

    }

    @FXML
    public void addUtilisateur(){
        String password="123456";
        Utilisateur user=new Utilisateur(
                "admin", Fonction.Admin,
                "895127236",
                BcryptUtil.hashPassword(password),
                "belamard@gmail.com"
        );
        us= new utilisateurService();
        us.save(user);
    }

    public void getTableauUsers(){
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        fonction.setCellValueFactory(new PropertyValueFactory<>("fonction"));
        action.setCellFactory(col->new TableCell<>(){
            Button btnM= new Button("Mod");
            Button btnS= new Button("Sup");
            {
                btnM.setOnAction(e->{
                    Utilisateur utili=getTableView().getItems().get(getIndex());
                    System.out.println(utili.getUsername());
                });

                btnS.setOnAction(e->{
                    Utilisateur utili=getTableView().getItems().get(getIndex());
                    System.out.println(utili.getUsername());
                });
            }


        });

    }

    public void getDataUser(Utilisateur utilisateur){
        id= utilisateur.getId();
        fieldUsername.setText(utilisateur.getUsername());
        fieldEmail.setText(utilisateur.getEmail());
        fieldPhone.setText(utilisateur.getTelephone());
        password.setText(utilisateur.getMotDePasse());
        confirme.setText("");
    }
}
