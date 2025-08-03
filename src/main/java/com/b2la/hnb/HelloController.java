package com.b2la.hnb;

import com.b2la.hnb.util.Stockage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;

public class HelloController {
    @FXML
    private Label errorTitle;

    @FXML
    private Panel panelLoading;

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
        if(phoneField.getText().length()<4){

        }
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
        throw new RuntimeException(messageError);
    }



}