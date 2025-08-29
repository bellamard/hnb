package com.b2la.hnb.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CalculerController {
    @FXML
    TextField totalFacture, montantRecu, taux;
    @FXML
    Label description;

    public void initialize(){
        montantRecu.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        totalFacture.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));

        taux.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) { // Accepte uniquement les chiffres
                return change;
            }
            return null; // Rejette les autres entrées
        }));
    }

    public void afficher(double totalFactu){
        totalFacture.setText(String.valueOf(totalFactu));
    }


    private Double calculerTaux(){
        if(!montantRecu.getText().isEmpty()||!taux.getText().isEmpty()){
            Double mRecu=Double.valueOf(montantRecu.getText());
            Double tConversion=Double.valueOf(taux.getText());

            if(tConversion<=0)throw new RuntimeException("Votre taux est incorrect!!!");
            if(mRecu<=0)throw new RuntimeException("Votre taux est incorrect!!!");

            return mRecu*tConversion;
        }
        throw new RuntimeException("remplissez les champs");
    }

    @FXML
    private void calculeReste(){
        Double totalF= Double.valueOf(totalFacture.getText());
        Double calcule=calculerTaux()-totalF;
        if(totalF>calculerTaux()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Probleme sur  montant");
            alert.setHeaderText("votre montant est inferieur");
            alert.setContentText("veuillez svp ajouter un montant de "+calcule+" CDF");
            alert.showAndWait();
            throw new RuntimeException("le montant recu est inferieur");
        }
        String descript = String.format(
                "💰 Facture: %.2f | Reçu: %.2f | Reste: %.2f",
                totalF, calculerTaux(), calcule
        );
        description.setText(descript);

    }

}
