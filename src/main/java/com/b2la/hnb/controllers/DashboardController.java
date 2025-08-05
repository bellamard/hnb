package com.b2la.hnb.controllers;


import com.b2la.hnb.util.Stockage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;

import javafx.scene.control.Label;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    @FXML
    private Label username, fonction, dateHeure;
    @FXML
    private Button facturation, produit, cloture, depense, utilisateur;

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


}

