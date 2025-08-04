package com.b2la.hnb;

import com.b2la.hnb.models.Utilisateur;
import com.b2la.hnb.services.utilisateurService;
import com.b2la.hnb.util.Stockage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.IOException;

import static javafx.application.Platform.runLater;

public class HelloController {
    @FXML
    private Label errorTitle;

    @FXML
    private Pane panelLoading;

    @FXML
    private Button connexion, fermer;
    @FXML
    private TextField phoneField;
    @FXML
    private PasswordField passwordField;


    @FXML
    protected void handleKeyPressed(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            onConnexion();
        }
    }
    @FXML
    private void onConnexion() {
        runLater(()->{
            if(phoneField.getText().length()<=4||phoneField.getText().length()>13) {
                viewError("le numero est incorrect");
                return;
            }
            if(passwordField.getText().isEmpty()) {
                viewError("votre mots de passe est inferieur a la valeur par defaut");
                return;
            }
            String password=passwordField.getText();
            String phone=phoneField.getText();
            panelLoading.setVisible(true);
            utilisateurService us= new utilisateurService();
            Utilisateur user= us.login(phone, password);
            if(user!=null){
                Stockage stock=new Stockage();
                stock.setUsername(user.getUsername());
                stock.setFonction(String.valueOf(user.getFonction()));
            }

        });

    }


    @FXML
    protected void onClose() {
        Stockage.reset();
        System.exit(0);
    }

    private void viewError(String messageError){
        errorTitle.setText(messageError);
        errorTitle.setVisible(true);
        panelLoading.setVisible(false);
        System.out.println(messageError);
        throw new RuntimeException(messageError);
    }



}