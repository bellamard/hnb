package com.b2la.hnb.controllers;


import com.b2la.hnb.util.Stockage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javafx.scene.control.Label;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    @FXML
    private Label username, fonction, dateHeure;
    @FXML
    private Button facturation, produit, cloture, depense, utilisateur;
    @FXML
    private VBox homeLayout,facturationLayout, produitLayout, depenseLayout, clotureLayout, parametreLayout, loadingLayout;

    public void initialize() {
        recoveryUsername();
        viewDateTime();
    }

    public void recoveryUsername() {
        Stockage stock = new Stockage();
        username.setText(stock.getUsername());
        fonction.setText(stock.getUsername());
    }

    public void viewDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalDateTime now = LocalDateTime.now();
                    dateHeure.setText(now.format(formatter));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }


    private void cardLayout(String layout){
        switch (layout){
            case "home":
                homeLayout.setVisible(true);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(false);
                break;
            case "produit":
                homeLayout.setVisible(false);
                produitLayout.setVisible(true);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(false);
                break;
            case "depense":
                homeLayout.setVisible(false);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(true);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(false);
                break;
            case "cloture":
                homeLayout.setVisible(false);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(true);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(false);
                break;
            case "facture":
                homeLayout.setVisible(false);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(true);
                parametreLayout.setVisible(false);
                break;
            case "parametre":
                homeLayout.setVisible(false);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(true);
                break;
            default:
                loadingLayout.setVisible(true);
                homeLayout.setVisible(false);
                produitLayout.setVisible(false);
                depenseLayout.setVisible(false);
                clotureLayout.setVisible(false);
                facturationLayout.setVisible(false);
                parametreLayout.setVisible(false);
                break;

        }
    }

}

