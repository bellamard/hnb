package com.b2la.hnb;

import com.b2la.hnb.controllers.utilisateurController;
import com.b2la.hnb.models.Utilisateur;
import com.b2la.hnb.services.utilisateurService;
import com.b2la.hnb.util.BcryptUtil;
import com.b2la.hnb.util.Stockage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TextField nameField;
    @FXML
    private PasswordField passwordField;

    utilisateurController uc;


    @FXML
    protected void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onConnexion();
        }
    }

    @FXML
    private void onConnexion() {
        new Thread(() -> {
            {
                runLater(() -> panelLoading.setVisible(true));
                if (nameField.getText().length() <= 2 || nameField.getText().length() > 50) {
                    viewError("le nom est incorrect");
                    return;
                }
                if (passwordField.getText().isEmpty()) {
                    viewError("votre mots de passe est inferieur a la valeur par defaut");
                    return;
                }
                String username = nameField.getText();
                String password = passwordField.getText();
                try {
                    utilisateurService us = new utilisateurService();
                    Utilisateur user = us.login(username, password);

                    Stockage stock = new Stockage();
                    stock.setUsername(user.getUsername());
                    stock.setFonction(String.valueOf(user.getFonction()));


                } catch (RuntimeException e) {
                    viewError(e.toString());
                }


            }
        }).start();


    }


    @FXML
    protected void onClose() {
        Stockage.reset();
        System.exit(0);
    }

    private void viewError(String messageError) {
        runLater(() -> {
            errorTitle.setText(messageError.toString().substring(55));
            errorTitle.setVisible(true);
            panelLoading.setVisible(false);
            System.out.println(messageError);
        });
        throw new RuntimeException(messageError);
    }


}